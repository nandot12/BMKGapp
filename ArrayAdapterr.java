package id.co.imastudio.bmkgapp22W;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

import id.co.imastudio.bmkgapp22W.Model.Iklim;

/**
 * Created by nandoseptianhusni on 10/21/17.
 */

class ArrayAdapterr extends ArrayAdapter<Iklim> {

   AnalisisIklim mapsActivity ;
    int item_auto ;
    ArrayList<Iklim> data ;

    private ArrayList<Iklim> itemsAll;
    private ArrayList<Iklim> suggestions;
    public ArrayAdapterr(AnalisisIklim mapsActivity, int item_auto, ArrayList<Iklim> data) {
        super(mapsActivity,item_auto,data);
        this.mapsActivity = mapsActivity ;
        this.item_auto = item_auto ;
        this.data = data ;
        this.itemsAll = (ArrayList<Iklim>) data.clone();
        this.suggestions = new ArrayList<Iklim>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = LayoutInflater.from(mapsActivity).inflate(item_auto,parent,false);

        TextView textview = v.findViewById(R.id.textname);

        textview.setText(data.get(position).getNama());

        return v;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((Iklim)(resultValue)).getNama();
            return str;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                suggestions.clear();
                for (Iklim customer : itemsAll) {
                    if(customer.getNama().toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(customer);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Iklim> filteredList = (ArrayList<Iklim>) results.values;
            if(results != null && results.count > 0) {
                clear();
                for (Iklim c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };


    @Override
    public int getCount() {
        return data.size();
    }


}
