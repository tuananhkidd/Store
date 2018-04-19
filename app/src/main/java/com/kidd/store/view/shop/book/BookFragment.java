package com.kidd.store.view.shop.book;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kidd.store.R;
import com.kidd.store.SQLiteHelper.DBManager;
import com.kidd.store.adapter.BookAdapter;
import com.kidd.store.models.Book;
import com.kidd.store.models.Category;
import com.kidd.store.presenter.book.BookPresenter;
import com.kidd.store.presenter.book.BookPresenterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookFragment extends Fragment implements BookFragmentView {

    RecyclerView rcv_book;
    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton floatingActionButton;
    DBManager db;
    BookAdapter adapter;
    List<Book> lsBook;
    BookPresenter presenter;

    public BookFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_book, container, false);
        initWidget(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new BookPresenterImpl(getActivity(),this);
        presenter.loadAllBook();
    }

    public void initWidget(View view){
        rcv_book = view.findViewById(R.id.rcv_book);
        swipeRefreshLayout = view.findViewById(R.id.swipRefresh);
        floatingActionButton = view.findViewById(R.id.flb_add);

        floatingActionButton.setOnClickListener(mOnClick);

        db = new DBManager(getActivity());
        lsBook = db.getAllBook();
        adapter = new BookAdapter(getActivity(),lsBook);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rcv_book.setLayoutManager(manager);
        rcv_book.setAdapter(adapter);

    }

    View.OnClickListener mOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setTitle("Add Book");
            dialog.setContentView(R.layout.dialog_add_book);
            final EditText edt_title;
            final EditText edt_author;
            final EditText edt_publisher;
            final EditText edt_price;
            final Spinner spinner;
            
            Button btn_insert_book;

            edt_title = dialog.findViewById(R.id.edt_title);
            edt_author = dialog.findViewById(R.id.edt_author);
            edt_publisher = dialog.findViewById(R.id.edt_publisher);
            edt_price = dialog.findViewById(R.id.edt_price);
            spinner = dialog.findViewById(R.id.spinner);
            btn_insert_book = dialog.findViewById(R.id.btn_insert_book);

            final List<Category> lsCategory = db.getAllCategory();
            List<String> ls = new ArrayList<>();
            for(Category category:lsCategory){
                ls.add(category.getTitle());
            }
            final String[] categoryID = {null};

            ArrayAdapter<String> adapter1 =
                    new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,ls);

            spinner.setAdapter(adapter1);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    categoryID[0] = lsCategory.get(i).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            btn_insert_book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UUID uuid = UUID.randomUUID();
                    Book book = new Book(uuid.toString(),edt_title.getText().toString(),edt_author.getText().toString(),
                            edt_publisher.getText().toString(),Integer.parseInt(edt_price.getText().toString()));
                    db.insertBook(book,categoryID[0]);

                    lsBook.clear();
                    lsBook.addAll(db.getAllBook());
                    adapter.notifyDataSetChanged();

                    Toast.makeText(getActivity(), "Successfully!!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
         

            dialog.show();
        }
    };

    @Override
    public void loadAllBooks(List<Book> list) {
        Log.i("listbook", "loadAllBooks: " + list.size());
        lsBook.addAll(list);
    }

}
