package nihongo_deb.com.cftvaluteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<JSONObject> {
    int listLayout;
    ArrayList<JSONObject> valutes;
    Context context;

    public ListViewAdapter(Context context, int listLayout, int field, ArrayList<JSONObject> valutes){
        super(context, listLayout, field, valutes);
        this.context = context;
        this.listLayout = listLayout;
        this.valutes = valutes;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listViewItem = inflater.inflate(listLayout, null, false);
        TextView name = listViewItem.findViewById(R.id.tv_name);
        TextView value = listViewItem.findViewById(R.id.tv_value);
        TextView nominal = listViewItem.findViewById(R.id.tv_nominal);
        try{
            name.setText(valutes.get(position).getString("Name"));
            nominal.setText("Номинал: "+ valutes.get(position).getString("Nominal"));
            value.setText(valutes.get(position).getString("Previous") + " -> " + valutes.get(position).getString("Value"));
        }catch (JSONException je){
            je.printStackTrace();
        }
        return listViewItem;
    }

}
