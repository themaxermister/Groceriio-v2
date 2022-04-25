package com.example.newgroceriio.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newgroceriio.Models.Product;
import com.example.newgroceriio.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> implements Filterable{

    private Context context;
    private ArrayList<Product> products;
    List<Product> allProducts;
    private OnProductListener mOnProductListener;
    Filter filter;


    public ProductAdapter(Context context, ArrayList<Product> _products, OnProductListener onProductListener){
        this.mOnProductListener = onProductListener;
        this.context = context;
        this.products = _products;
        allProducts = new ArrayList<>(_products);
        filter = new Filter() {

            //runs on background thread
            @Override
            protected FilterResults performFiltering(CharSequence charSequence){
                System.out.println("performFiltering: " + charSequence.toString());
                for (int i = 0; i< allProducts.size(); i++){
                    System.out.println(allProducts.get(i).getProductName());
                }
                ArrayList<Product> filteredList = new ArrayList<Product>();

                if (charSequence.length() == 0){
                    System.out.println("is empty");
                    filteredList.addAll(allProducts);
                    System.out.println(filteredList);
                } else {
                    for (Product product: allProducts ){
                        if (product.getProductName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            filteredList.add(product);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();

                filterResults.values = filteredList;
                return filterResults;

            }

            //runs on ui thread
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                System.out.println("publishResults::filterResults");
                System.out.println(filterResults.values);
                products.clear();
                products.addAll((Collection<? extends Product>) filterResults.values);

                notifyDataSetChanged();
            }

        };
    }
    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        // Get product view from layout
        View view = LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false);
        return new ProductHolder(view, mOnProductListener);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position){

        Product product = products.get(position);
        holder.setDetails(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }


    // Holder Class

    class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        OnProductListener onProductListener;
        ImageView imageView;
        TextView productName, productPrice, productMetric;

        ProductHolder(View productView, OnProductListener onProductListener){
            super(productView);

            productName = productView.findViewById(R.id.productPgName);
            productPrice = productView.findViewById(R.id.productPgPrice);
            imageView = productView.findViewById(R.id.productListImage);
            productMetric = productView.findViewById(R.id.productPgMetric);

            this.onProductListener = onProductListener;
            productView.setOnClickListener(this);

        }
        void setDetails(Product product){
            productName.setText(product.getProductName());
            productPrice.setText(String.valueOf(product.getPrice()));
            productMetric.setText(String.valueOf(product.getMetric()));
            Glide.with(context)
                    .load(product.getImgUrl())
                    .into(imageView);

        }

        @Override
        public void onClick(View view) {
            onProductListener.onProductClick(getAdapterPosition());
        }
    }

    public interface OnProductListener{
        void onProductClick(int position);
    }


}

