package slidnerd.vivz.bucketdrops;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import slidnerd.vivz.bucketdrops.beans.Drop;

/**
 * Created by Kevin on 3/7/2016.
 */
public class DialogAdd extends DialogFragment {
    private ImageButton mBtnClose;
    private EditText mInputWhat;
    private DatePicker mInputWhen;
    private Button mBtnAdd;

    public DialogAdd() {
    }

    private View.OnClickListener mBtnCloseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();

            switch (id)
            {
                case R.id.btn_add_it:
                    addAction();
                    break;
            }
            dismiss();
        }
    };

    private void addAction() {
        //Get the value of the goal or to-do
        //Get the time when it was added
        String what = mInputWhat.getText().toString();
        long now = System.currentTimeMillis();

        RealmConfiguration configuration= new RealmConfiguration.Builder(getActivity()).build();
        Realm.setDefaultConfiguration(configuration);
        Realm realm =Realm.getDefaultInstance();

        Drop drop = new Drop(what,now,0,false);

        realm.beginTransaction();
        realm.copyToRealm(drop);
        realm.commitTransaction();

        realm.close();

        /*
        RealmResults<Drop> results = realm.where(Drop.class).findAll();

        for(Drop d: results)
        {
            Log.d("results",d.getWhat());
        }
        */

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.dialog_add,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBtnClose =(ImageButton) view.findViewById(R.id.btn_close);
        mInputWhat = (EditText) view.findViewById(R.id.et_drop);
        mInputWhen = (DatePicker) view.findViewById(R.id.bpv_date);
        mBtnAdd = (Button) view.findViewById(R.id.btn_add_it);

        mBtnAdd.setOnClickListener(mBtnCloseListener);
        mBtnClose.setOnClickListener(mBtnCloseListener);
    }
}
