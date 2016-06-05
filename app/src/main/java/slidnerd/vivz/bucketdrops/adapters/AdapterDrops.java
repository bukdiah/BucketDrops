package slidnerd.vivz.bucketdrops.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import slidnerd.vivz.bucketdrops.R;
import slidnerd.vivz.bucketdrops.beans.Drop;

/**
 * Created by Kevin on 4/3/2016.
 */
public class AdapterDrops extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeListener {

    public  static int ITEM = 0;
    public static final int FOOTER =1;
    private MarkListener mMarkListener;
    private LayoutInflater mInflater;
    private RealmResults<Drop> mResults;
    private Realm mRealm;
    private AddListener mAddListener;

    public AdapterDrops(Context context, Realm realm,RealmResults<Drop> results)
    {
        mInflater = LayoutInflater.from(context);
        //mResults = results;
        mRealm = realm;
        update(results);
    }

    public AdapterDrops(Context context, Realm realm,RealmResults<Drop> results, AddListener listener)
    {
        mInflater = LayoutInflater.from(context);
        //mResults = results;
        mRealm = realm;
        update(results);
        mAddListener = listener;
    }

    public AdapterDrops(Context context, Realm realm,RealmResults<Drop> results, AddListener listener, MarkListener markListener)
    {
        mInflater = LayoutInflater.from(context);
        //mResults = results;
        mRealm = realm;
        mAddListener = listener;
        mMarkListener = markListener;
    }

    public void setAddListener (AddListener listener)
    {
        mAddListener = listener;
    }

    public void update (RealmResults<Drop> results)
    {
      mResults = results;
        notifyDataSetChanged();
    }

    public static ArrayList<String> generateValues()
    {
        ArrayList<String> dummyValues = new ArrayList<>();
        for(int i = 1; i < 101; i++)
        {
            dummyValues.add("Item "+ i);
        }
        return dummyValues;
    }

    @Override
    public int getItemViewType(int position) {
        if(mResults==null || position < mResults.size())
        {
            return ITEM;
        }
        else
        {
            return FOOTER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == FOOTER)
        {
            View view = mInflater.inflate(R.layout.footer,parent,false);
           // FooterHolder holder = new FooterHolder(view);
            return new FooterHolder(view);
        }
        else
        {
            View view = mInflater.inflate(R.layout.row_drop,parent,false);
            //DropHolder holder = new DropHolder(view);
            return new DropHolder(view, mMarkListener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof DropHolder)
        {
            DropHolder dropHolder = (DropHolder) holder;
            Drop drop = mResults.get(position);
            dropHolder.mTextWhat.setText(drop.getWhat());
        }

    }

    @Override
    public int getItemCount() {
        //+ 1 because of the footer
        if(mResults==null || mResults.isEmpty())
        {
            return 0;
        }
        else
            return mResults.size() + 1;
    }

    @Override
    public void onSwipe(int position) {
        if(position<mResults.size())
        {
            mRealm.beginTransaction();
            mResults.get(position).removeFromRealm();
            mRealm.commitTransaction();
            notifyItemRemoved(position);
        }

    }

    public static class DropHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTextWhat;
        TextView mTextWhen;
        MarkListener mMarkListener;

        public DropHolder(View itemView, MarkListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTextWhat = (TextView) itemView.findViewById(R.id.tv_what);
            mTextWhen = (TextView) itemView.findViewById(R.id.tv_when);
            mMarkListener = listener;
        }

        @Override
        public void onClick(View v) {
            mMarkListener.onMark(getAdapterPosition());
        }
    }

    public class FooterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button mBtnAdd;

        public FooterHolder(View itemView) {
            super(itemView);
            mBtnAdd = (Button) itemView.findViewById(R.id.btn_footer);
            mBtnAdd.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mAddListener.add();
        }
    }
}
