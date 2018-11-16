package com.ea222.firelistv4.Activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;
import com.ea222.firelistv4.Adapter.MyViewHolder;
import com.ea222.firelistv4.Model.ReminderPOJO;
import com.ea222.firelistv4.R;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static com.ea222.firelistv4.Model.ReminderPOJO.OrderByType;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private FloatingActionButton button_fab;

    private DatabaseReference mDatabase;

    //private FirebaseDatabase mRef;

    private FirebaseAuth mAuth;

    private RecyclerView recyclerView;

    private String type, subtype, note, id;

    private ArrayList<ReminderPOJO> POJOs;

    // to save sort settings on device
    SharedPreferences mpref;
    // to sort cardviews inside recyclerView
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ReminderList");

        //action bar
        // ActionBar actionBar = getSupportActionBar();

        // list to group cardviews by type in mainscreen.
        // the method(s) using this doesnt not work correctly atm
        POJOs = new ArrayList<>();
        // sorting
        // for remembering the sorted cardview

        //mpref = this.getSharedPreferences("Sorted Data", MODE_PRIVATE);
        mpref = getSharedPreferences("Sorted Data", MODE_PRIVATE);
        String mSort = mpref.getString("Sort","ascending");

//        layoutManager=new LinearLayoutManager(this);
//
//        layoutManager.setStackFromEnd(true);
//        layoutManager.setReverseLayout(true);

        if(mSort.equals("ascending")){
            layoutManager=new LinearLayoutManager(this);
            // adds new cardview (Data objects) at the bottom
            layoutManager.setReverseLayout(false);
            layoutManager.setStackFromEnd(false);
        }
        //recyclerView
        recyclerView=findViewById(R.id.recycler_home);

        recyclerView.setHasFixedSize(true);

        ///////  showData();

        //make layout as LinearLayout
        recyclerView.setLayoutManager(layoutManager);


        /*
FIRE BASE START
 */
        mAuth=FirebaseAuth.getInstance();

        FirebaseUser mUser=mAuth.getCurrentUser();
        String uId=mUser.getUid();

        //query firebasedatabase
        //    mRef = FirebaseDatabase.getInstance();

        //   mDatabase= FirebaseDatabase.getInstance().getReference().child("ReminderList").child(uId);
        // cannot just do fdb.gt().gr() --> FireListv4
        mDatabase= FirebaseDatabase.getInstance().getReference().child(uId);

        mDatabase.keepSynced(true);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        button_fab=findViewById(R.id.use_fab);

        button_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog();
            }
        });

    }
    // search firebase for string comparisons
//    private void firebaseSearch(String searchText){
//
//        String query = searchText.toLowerCase();
//
//        Query firebaseSearchQuery = mDatabase.orderByChild("search").startAt(query).endAt(query + "\uf8ff");


//            @Override
//            protected void populateViewHolder(MyViewHolder viewHolder, ReminderPOJO model, int position) {
//                //model.getId() not used currently
//            viewHolder.setDetails(getApplicationContext(), model.getType(), model.getSubtype(),
//                    model.getNote(), model.getDate());
//            }
//
//            @Override
//            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                MyViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
//                viewHolder.setOnClickListener(new MyViewHolder.ClickListener(){
//                    @Override
//                    public void onItemClick(View view, int position){
//
//                    }
//
//                }
//
//
//
//            }
//        FirebaseRecyclerAdapter<ReminderPOJO,MyViewHolder> adapter=new FirebaseRecyclerAdapter<ReminderPOJO, MyViewHolder>
//                (
//                        //model class object
//                        ReminderPOJO.class,
//                        //tells listview what layout to use for each row
//                        //cardview.xml
//                        R.layout.cardview,
//                        //makes TextViews of arguments from ReminderPOJO.class
//                        MyViewHolder.class,
//
//                        //firebase instance reference
//                       // mDatabase
//
//                        firebaseSearchQuery
//                )
//        {

//            final Comparator<ReminderPOJO> OrderByType = new Comparator<ReminderPOJO>() {
//            @Override
//            public int compare(ReminderPOJO o1, ReminderPOJO o2) {
//                //ascending order
//                return o1.getType().compareTo(o2.getType());
//            }
//        };

//            @Override
//            protected void populateViewHolder(MyViewHolder viewHolder, final ReminderPOJO model, final int position) {

    // data objects are being appended to arraylist
    //   ReminderPOJOs.add(model);
    //    Collections.sort(POJOs, ReminderPOJO.OrderByType);

//                System.out.println("PRINTING HERE: " + model);
//                System.out.println("MORE HERE" + POJOs);
//                viewHolder.setDate(model.getDate());
//                viewHolder.setType(model.getType());
//                viewHolder.setNote(model.getNote());
//                viewHolder.setSubtype(model.getSubtype());
//
//
//                viewHolder.myview.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        id=getRef(position).getKey();
//                        type=model.getType();
//                        note=model.getNote();
//                        subtype=model.getSubtype();
//
//                        updateData();
//                    }
//                });
//
//            }
//        };

    //adapter.notifyDataSetChanged();
    //  adapter = new FirebaseRecyclerAdapter<ReminderPOJO, MyViewHolder>;
//
//        recyclerView.setAdapter(adapter);
//
//
//
//        }



    // opens add reminder xml
    private void customDialog(){

        AlertDialog.Builder mydialog=new AlertDialog.Builder(MainActivity.this);

        LayoutInflater inflater=LayoutInflater.from(MainActivity.this);
        View myview=inflater.inflate(R.layout.add_reminder,null);

        final AlertDialog dialog=mydialog.create();

        dialog.setView(myview);

        final EditText type=myview.findViewById(R.id.edt_type);
        final EditText subtype=myview.findViewById(R.id.edt_subtype);
        final EditText note=myview.findViewById(R.id.edt_note);
        Button btnSave=myview.findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mType=type.getText().toString().trim();
                String mSubtype=subtype.getText().toString().trim();
                String mNote=note.getText().toString().trim();

                if (TextUtils.isEmpty(mType)){
                    type.setError("Cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(mSubtype)){
                    subtype.setError("Cannot be blank");
                    return;
                }
                if (TextUtils.isEmpty(mNote)){
                    note.setError("Cannot be blank");
                    return;
                }

                //push() generates unique key for each child added chronologically
                //getKey returns value of the unique timestamped key
                //this id will be useful for read/write of listed data
                String id=mDatabase.push().getKey();

                String date= DateFormat.getDateInstance().format(new Date());
                ReminderPOJO data = new ReminderPOJO(date, id, mType, mSubtype, mNote);

                //using setValue for initial values only
                mDatabase.child(id).setValue(data);
                /*
                mDatabase.child(id) is the id of the child node under User_ID node
                 */

                //System.out.println("Printing item id? : " + mDatabase.child(id));

                Toast.makeText(getApplicationContext(),"Reminder Added",Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });


        dialog.show();

    }

    // load all data into recycler view on application start
    @Override
    protected void onStart() {
        super.onStart();


        /*
        T - The Java class that maps to the type of objects stored in the Firebase location.
VH - The ViewHolder class that contains the Views in the layout that is shown for each object.

This class is a generic way of backing an RecyclerView with a Firebase location.
It handles all of the child events at the given Firebase location.
It marshals received data into the given class type. To use this class in your app,
subclass it passing in all required parameters and implement the populateViewHolder method.
         */


        FirebaseRecyclerAdapter<ReminderPOJO,MyViewHolder> adapter=new FirebaseRecyclerAdapter<ReminderPOJO, MyViewHolder>
                (
                        //model class object
                        ReminderPOJO.class,
                        //tells listview what layout to use for each row
                        //cardview.xml
                        R.layout.cardview,
                        //makes TextViews of arguments from Data.class
                        MyViewHolder.class,
                        //firebase instance reference
                        mDatabase
                )
        {

//            final Comparator<ReminderPOJO> OrderByType = new Comparator<ReminderPOJO>() {
//            @Override
//            public int compare(ReminderPOJO o1, ReminderPOJO o2) {
//                //ascending order
//                return o1.getType().compareTo(o2.getType());
//            }
//        };

            @Override
            protected void populateViewHolder(MyViewHolder viewHolder, final ReminderPOJO model, final int position) {

                // data objects are being appended to arraylist
                POJOs.add(model);
                //       Collections.sort(POJOs, Data.OrderByType);

//              System.out.println("PRINTING HERE: " + model);
//              System.out.println("MORE HERE" + POJOs);
                viewHolder.setDate(model.getDate());
                viewHolder.setType(model.getType());
                viewHolder.setNote(model.getNote());
                viewHolder.setSubtype(model.getSubtype());

                viewHolder.myview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        id=getRef(position).getKey();
                        type=model.getType();
                        subtype=model.getSubtype();
                        note=model.getNote();

                        updateData();
                    }
                });

            }
        };

        //adapter.notifyDataSetChanged();
        //  adapter = new FirebaseRecyclerAdapter<ReminderPOJO, MyViewHolder>;

        recyclerView.setAdapter(adapter);

    }




    public void updateData(){

        AlertDialog.Builder mydialog=new AlertDialog.Builder(MainActivity.this);

        LayoutInflater inflater=LayoutInflater.from(MainActivity.this);

        View mView=inflater.inflate(R.layout.update_reminder,null);

        final AlertDialog dialog=mydialog.create();

        dialog.setView(mView);

        final EditText edt_Type=mView.findViewById(R.id.edt_type_upd);
        final EditText edt_Subtype=mView.findViewById(R.id.edt_subtype_upd);
        final EditText edt_Note=mView.findViewById(R.id.edt_note_upd);

        edt_Type.setText(type);
        edt_Type.setSelection(type.length());

        edt_Subtype.setText(subtype);
        edt_Subtype.setSelection(subtype.length());

        edt_Note.setText(note);
        edt_Note.setSelection(note.length());

        Button btnUpdate=mView.findViewById(R.id.btn_SAVE_upd);
        Button btnDelete=mView.findViewById(R.id.btn_delete_upd);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                for (int i = 0; i < POJOs.size(); ++i){
//                    System.out.print("HERE IS THE KEY: " + POJOs.get(i));
//            }
                type=edt_Type.getText().toString().trim();

                subtype=edt_Subtype.getText().toString().trim();

                note=edt_Note.getText().toString().trim();

                String date=DateFormat.getDateInstance().format(new Date());

                ReminderPOJO data = new ReminderPOJO(date, id, type, subtype, note);

                mDatabase.child(id).setValue(data);

                dialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabase.child(id).removeValue();

                dialog.dismiss();

            }
        });

        dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate menue will add items to the actionbar
        getMenuInflater().inflate(R.menu.sort_cardview,menu);
//        MenuItem item = menu.findItem(R.id.log_sort);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
//

        //Submite change always before change or get anonymous class error
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                firebaseSearch(query);
//                return false;
//            }
//
//            @Override
//            // filters while typing
//            public boolean onQueryTextChange(String newText) {
//                firebaseSearch(newText);
//                return false;
//            }
//
//        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        switch (item.getItemId()){
//            case R.id.sort_cardview:
////                mAuth.signOut();
//                Collections.sort(POJOs,ReminderPOJO.OrderByType);
////                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
//                return true;
//        }
//        return super.onOptionsItemSelected(item);


        int id = item.getItemId();
        if(id == R.id.sort_cardview){
            showSortDialog();
            Collections.sort(POJOs, OrderByType);
//            Collections.sort(POJOs, new Comparator<ReminderPOJO>() {
//                @Override
//                public int compare(ReminderPOJO o1, ReminderPOJO o2) {
//                    return o1.getType().compareTo(o2.getType());
//                }
//            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showSortDialog() {
        // options in dialog display
        String[] sortOpt = {"ascending"};

        //alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort by")
                .setIcon(R.drawable.sort_icon)
                .setItems(sortOpt, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        if(which == 0){
                            SharedPreferences.Editor editor = mpref.edit();
                            editor.putString("Sort", "ascending");
                            editor.apply();
                            // this restarts activity
                            recreate();
                            //   POJOs.add(model);
                        }
                    }
                });
        //try both, one will work..
        builder.show();
        //builder.create().show();
    }
}