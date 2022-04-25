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
import com.example.newgroceriio.Models.Category;
import com.example.newgroceriio.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> implements Filterable {

    private Context context;
    private ArrayList<Category> categories;
    private List<Category> allCategories;
    private CategoryAdapter.OnCategoryListener mOnCategoryListener;
    Filter filter;


    public CategoryAdapter(Context context, ArrayList<Category> _categories, CategoryAdapter.OnCategoryListener onCategoryListener){
        this.mOnCategoryListener = onCategoryListener;
        this.context = context;
        this.categories = _categories;
        allCategories = new ArrayList<>(_categories);
        filter = new Filter() {

            //runs on background thread
            @Override
            protected FilterResults performFiltering(CharSequence charSequence){
                System.out.println("performFiltering: " + charSequence.toString());
                ArrayList<Category> filteredList = new ArrayList<>();

                if (charSequence.length() == 0){
                    System.out.println("is empty");
                    filteredList.addAll(allCategories);
                    System.out.println(filteredList);
                } else {
                    for (Category category: allCategories ){
                        if (category.getCategoryType().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            filteredList.add(category);
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
                categories.clear();
                categories.addAll((Collection<? extends Category>) filterResults.values);

                notifyDataSetChanged();
            }

        };
    }
    @NonNull
    @Override
    public CategoryAdapter.CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        // Get product view from layout
        View view = LayoutInflater.from(context).inflate(R.layout.category_list_item, parent, false);
        return new CategoryAdapter.CategoryHolder(view, mOnCategoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryHolder holder, int position){

        Category category = categories.get(position);
        holder.setDetails(category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }


    // Holder Class

    class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CategoryAdapter.OnCategoryListener onCategoryListener;
        TextView categoryType;


        CategoryHolder(View categoryView, CategoryAdapter.OnCategoryListener onCategoryListener){
            super(categoryView);
            categoryType = categoryView.findViewById(R.id.categoryTypeName);

            this.onCategoryListener = onCategoryListener;
            categoryView.setOnClickListener(this);

        }
        void setDetails(Category category){
            categoryType.setText(category.getCategoryType());

        }

        @Override
        public void onClick(View view) {
            onCategoryListener.onCategoryClick(getAdapterPosition());
        }
    }

    public interface OnCategoryListener{
        void onCategoryClick(int position);
    }

}
