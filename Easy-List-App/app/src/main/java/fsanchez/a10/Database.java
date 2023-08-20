package fsanchez.a10;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Database {

    /**
     * add function will add user input to the firebase database
     * @param db firebase database
     * @param selectedollection our collection of items
     * @param item item in a list
     */

    public static void add(FirebaseFirestore db, String selectedollection, ListItem item){
        Map<String, Object>  listItem = new HashMap<>();
        listItem.put("item", item);

        db.collection(selectedollection)
                .add(listItem)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("DATABASE", "Item added: " + documentReference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("DATABASE", "Failed to add item" + Arrays.toString(e.getStackTrace()));
                    }
                });
    }

    /**
     * getList function gets the firebase database created by the user
     * will put items in order from first entered to last
     * @param db firebase dataabase
     * @param selectedCollection our collection of items
     * @param items items of list
     * @param itemsAdapter adapter of items
     */
    public static void getList(FirebaseFirestore db, String selectedCollection
            , ArrayList<ListItem> items, ArrayAdapter<ListItem> itemsAdapter){

        db.collection(selectedCollection)
                .orderBy("Item.dttm")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot doc : task.getResult()){
                                long dttm = doc.getLong("item.dttm");
                                String item = doc.getString("item.item");
                                items.add(new ListItem(dttm, item));
                            }
                            itemsAdapter.notifyDataSetChanged();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("DATABASE", "Failed to get list: " + Arrays.toString(e.getStackTrace()));
                    }
                });


    }

    /**
     * removeItem function will remove an item from the firebase database
     * @param db firebase database
     * @param selectedCollection collection of items
     * @param items items of list
     * @param itemsAdapter adapter of items
     * @param removeItem remove the item from the database and list
     */


    public static void removeItem(FirebaseFirestore db, String selectedCollection
            , ArrayList<ListItem> items, ArrayAdapter<ListItem> itemsAdapter, ListItem removeItem){

        db.collection(selectedCollection).whereEqualTo("item.dttm", removeItem.getDttm())
                .whereEqualTo("item.item", removeItem.getItem())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot doc : queryDocumentSnapshots){
                            db.collection(selectedCollection).document(doc.getId()).delete();
                        }
                    }
                });
    }
}
