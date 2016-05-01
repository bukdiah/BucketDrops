package slidnerd.vivz.bucketdrops.widgets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import slidnerd.vivz.bucketdrops.extras.Util;

/**
 * Created by Kevin on 4/15/2016.
 */
public class BucketRecyclerView extends RecyclerView {
    private List<View> mNonEmptyViews = Collections.emptyList(); //Gonna be displayed when adapter has element(s)
    private List<View> mEmptyViews = Collections.emptyList();; //Gonna be displayed when adapter is empty

    private AdapterDataObserver mObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
         //   super.onChanged();
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
          //  super.onItemRangeChanged(positionStart, itemCount);
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
           // super.onItemRangeChanged(positionStart, itemCount, payload);
            toggleViews();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            //super.onItemRangeInserted(positionStart, itemCount);
            toggleViews();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            //super.onItemRangeRemoved(positionStart, itemCount);
            toggleViews();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            //super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            toggleViews();
        }
    };

    private void toggleViews() {
        if(getAdapter()!=null && !mEmptyViews.isEmpty()&&!mNonEmptyViews.isEmpty())
        {
            if(getAdapter().getItemCount()==0)
            {
                //sow all the empty views
                Util.showViews(mEmptyViews);
                //Hide the recyclerview
                setVisibility(View.GONE);

                //hide all the views which are meant to be hidden
                Util.hideViews(mNonEmptyViews);
            }
            else
            {
                //hide all the empty views
                Util.showViews(mNonEmptyViews);
                //show the recyclerview
                setVisibility(View.VISIBLE);

                //hide all the views which are meant to be hidden
                Util.hideViews(mEmptyViews);
            }
        }
    }

    //Initialize recyclerview from code
    public BucketRecyclerView(Context context) {
        super(context);
    }

    //Initialize recyclerview from XML
    public BucketRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BucketRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    //Register observer on setAdapter
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter!=null)
        {
            adapter.registerAdapterDataObserver(mObserver);
        }
        mObserver.onChanged();
    }

    //Hides Views if adapter is empty
    public void hideIfEmpty(View ...views) {
        mNonEmptyViews = Arrays.asList(views);
    }

    //Shows Views when adapter is empty
    public void showIfEmpty(View ...views) {
        mEmptyViews = Arrays.asList(views);
    }
}
