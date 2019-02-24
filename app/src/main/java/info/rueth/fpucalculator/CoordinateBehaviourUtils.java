package info.rueth.fpucalculator;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;

public class CoordinateBehaviourUtils {

    public static void enableDisableViewBehaviour (View view, CoordinatorLayout.Behavior<View> behaviour,
                                                   boolean enable) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
        params.setBehavior(behaviour);
        view.requestLayout();
        view.setVisibility((enable ? View.VISIBLE: View.GONE));
    }
}
