package com.androidbelieve.materialnavigationdrawer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidbelieve.materialnavigationdrawer.Fragments.Empty_fragment;
import com.androidbelieve.materialnavigationdrawer.Fragments.MY_details_Fragment;
import com.androidbelieve.materialnavigationdrawer.Fragments.Location;
import com.androidbelieve.materialnavigationdrawer.Fragments.LogOut_Fragment;
import com.androidbelieve.materialnavigationdrawer.Fragments.My_Rides_fragment;
import com.androidbelieve.materialnavigationdrawer.Fragments.Post_Ride_fragment;
import com.facebook.login.widget.ProfilePictureView;

import java.io.InputStream;
import java.net.URL;

public class Drawable_menu_Adapter extends RecyclerView.Adapter<Drawable_menu_Adapter.ViewHolder> {
    String[] titles;
    TypedArray icons;
    Context context;
    ProfilePictureView profilePictureView;
    TextView full_name;

    Bitmap bitmap;
    ImageView img;


    // The default constructor to receive titles,icons and context from Main_Page.
   Drawable_menu_Adapter(String[] titles, TypedArray icons, Context context){

            this.titles = titles;
            this.icons = icons;
            this.context = context;
    }


   /**
    *Its a inner class to Drawable_menu_Adapter Class.
    *This ViewHolder class implements View.OnClickListener to handle click events.
    *If the itemType==1 ; it implies that the view is a single row_item with TextView and ImageView.
    *This ViewHolder describes an item view with respect to its place within the RecyclerView.
    *For every item there is a ViewHolder associated with it .
    */

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        TextView  navTitle;
        ImageView navIcon;
        Context context;


        public ViewHolder(View drawerItem , int itemType , Context context){

            super(drawerItem);
            this.context = context;
            drawerItem.setOnClickListener(this);
            if(itemType==1){
                navTitle = (TextView) itemView.findViewById(R.id.tv_NavTitle);
                navIcon = (ImageView) itemView.findViewById(R.id.iv_NavIcon);


            }

        }

       /*
        *This defines onClick for every item with respect to its position.
        */

        @Override
        public void onClick(View v) {

            Main_Page mainActivityStas = (Main_Page)context;
            mainActivityStas.drawerLayout.closeDrawers();
            FragmentTransaction fragmentTransaction = mainActivityStas.getSupportFragmentManager().beginTransaction();

            switch (getPosition()){



                case 1:
                    Fragment myrides = new My_Rides_fragment();
                    fragmentTransaction.replace(R.id.containerView,myrides);
                    fragmentTransaction.commit();
                    break;

                case 2:
                    Fragment location = new Location();
                    fragmentTransaction.replace(R.id.containerView,location);
                    fragmentTransaction.commit();
                    break;
                case 3:
                    Fragment squadFragment = new MY_details_Fragment();
                    fragmentTransaction.replace(R.id.containerView,squadFragment);
                    fragmentTransaction.commit();
                    break;



                case 4:
                    Fragment logout = new LogOut_Fragment();
                    fragmentTransaction.replace(R.id.containerView,logout);
                    fragmentTransaction.commit();
                    break;

                case 5:
                    Fragment tableFragment = new Empty_fragment();
                    fragmentTransaction.replace(R.id.containerView,tableFragment);
                    fragmentTransaction.commit();
                    break;

            }
        }
    }


    @Override
    public Drawable_menu_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if(viewType==1){
                 View itemLayout =   layoutInflater.inflate(R.layout.drawer_item_layout,null);
                 return new ViewHolder(itemLayout,viewType,context);
            }
            else if (viewType==0) {

                View itemHeader = layoutInflater.inflate(R.layout.drawable_menu_header, null);


                img = (ImageView)itemHeader.findViewById(R.id.image);

                new LoadImage().execute("https://graph.facebook.com/"+MainActivity.id+"/picture?height=400&width=400&migration_overrides=%7Boctober_2012%3Atrue%7D");



                full_name = (TextView) itemHeader.findViewById(R.id.name);
                full_name.setText(MainActivity.fname);


                return new ViewHolder(itemHeader,viewType,context);
            }




        return null;
    }

   /**
    *This method is called by RecyclerView.Adapter to display the data at the specified position.�
    *This method should update the contents of the itemView to reflect the item at the given position.
    *So here , if position!=0 it implies its a row_item and we set the title and icon of the view.
    */


    @Override
    public void onBindViewHolder(Drawable_menu_Adapter.ViewHolder holder, int position) {

        if(position!=0){
            holder.navTitle.setText(titles[position - 1]);
            holder.navIcon.setImageResource(icons.getResourceId(position-1,-1));
        }

    }

     /**
      *It returns the total no. of items . We +1 count to include the header view.
      *So , it the total count is 5 , the method returns 6.
      *This 6 implies that there are 5 row_items and 1 header view with header at position zero.
      */

    @Override
    public int getItemCount() {
        return titles.length+1;
    }


   /**
    *This methods returns 0 if the position of the item is '0'.
    *If the position is zero its a header view and if its anything else
    *its a row_item with a title and icon.
    */

    @Override
    public int getItemViewType(int position) {
        if(position==0)return 0;
        else return 1;
    }



    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){


                img.setImageBitmap(getCircleBitmap(image));


            }else{

               // pDialog.dismiss();
               // Toast.makeText(Drawable_menu_Adapter.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }

        private Bitmap getCircleBitmap(Bitmap bitmap) {
            final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(output);

            final int color = Color.RED;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawOval(rectF, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

            bitmap.recycle();

            return output;
        }

    }


}
