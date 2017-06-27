package ir.mehdi.kelid.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridView;

import ir.mehdi.kelid.utils.Utils;

/**
 * Created by admin on 27/06/2017.
 */

public class PhotoGridView extends GridView {
    public PhotoGridView(Context context) {
        super(context);
    }

    public PhotoGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PhotoGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = getMeasuredHeight()+ Utils.dpToPx(getContext(),3)*(((int)AddPropetyActivity.property.images.size()/3)+1);
    }
}
