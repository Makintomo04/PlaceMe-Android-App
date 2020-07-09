package com.example.placemenetapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomCursorAdapter extends CursorAdapter {


    private LayoutInflater inflater;

    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View newView = inflater.inflate(R.layout.activity_results_list, parent, false);
        return newView;
    }

    @Override
    public void bindView(View newView, final Context context, final Cursor cursor) {
        DatabaseHelper db = new DatabaseHelper(context);
        SQLiteDatabase database = db.getWritableDatabase();

        ImageView companyImage = (ImageView) newView.findViewById(R.id.tempLogo);
        TextView textView_name = (TextView) newView.findViewById(R.id.placementTitle);
        TextView textView_companyName = (TextView) newView.findViewById(R.id.companyName);
        TextView textView_closingDate = (TextView) newView.findViewById(R.id.closingDate);
        TextView textView_type = (TextView) newView.findViewById(R.id.type);
        TextView textView_salary = (TextView) newView.findViewById(R.id.salary);
        TextView textView_location = (TextView) newView.findViewById(R.id.location);
        final ImageView imageView_favorite = (ImageView) newView.findViewById(R.id.favorite);

        String imgName = cursor.getString(2).toLowerCase();
        imgName = imgName.replace(" ", ""); // Get image for company name

        int resource = context.getResources().getIdentifier(imgName,
                "drawable", context.getPackageName());
        companyImage.setImageResource(resource);

        textView_name.setText(cursor.getString(1));
        textView_companyName.setText(cursor.getString(2));
        textView_closingDate.setText(cursor.getString(3));
        textView_type.setText(cursor.getString(4));
        textView_salary.setText(cursor.getString(5));
        textView_location.setText(cursor.getString(6));
        final int id = cursor.getInt(0);

        final boolean check = db.checkFav(id); // Check if placement stored in favorites

        if(check == true) {
            imageView_favorite.setImageResource(R.drawable.favorite);
        } else {
            imageView_favorite.setImageResource(R.drawable.favorite_empty);
        }

        imageView_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(context);
                SQLiteDatabase database = db.getWritableDatabase();

                if (check == true) { // If placement is set as a favorite, change to not favorite when clicked
                    db.removeFav(id);
                    imageView_favorite.setImageResource(R.drawable.favorite_empty);
                    Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show();
                    CustomCursorAdapter.this.changeCursor(cursor);
                    notifyDataSetChanged();
                } else {
                    db.addFav(id);
                    imageView_favorite.setImageResource(R.drawable.favorite);
                    Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show();
                    CustomCursorAdapter.this.changeCursor(cursor);
                    notifyDataSetChanged();
                }
            }
        });
    }
}
