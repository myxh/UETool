package me.ele.uetool;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;
import me.ele.uetool.base.DimenUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.view.Gravity.BOTTOM;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static me.ele.uetool.TransparentActivity.Type.*;

import androidx.annotation.IntDef;
import androidx.appcompat.app.AppCompatActivity;

import com.pitaya.mobile.uinspector.UInspector;

public class TransparentActivity extends AppCompatActivity {

    public static final String EXTRA_TYPE = "extra_type";

    private ViewGroup vContainer;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            finish();
            return;
        }
        Util.setStatusBarColor(getWindow(), Color.TRANSPARENT);
        Util.enableFullscreen(getWindow());
        setContentView(R.layout.uet_activity_transparent);

        vContainer = findViewById(R.id.container);

        final BoardTextView board = new BoardTextView(this);
        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UETool.getInstance().getTargetActivity().finish();
                finish();
            }
        });

        type = getIntent().getIntExtra(EXTRA_TYPE, TYPE_UNKNOWN);

        switch (type) {
            case TYPE_EDIT_ATTR:
                EditAttrLayout editAttrLayout = new EditAttrLayout(this);
                editAttrLayout.setOnDragListener(new EditAttrLayout.OnDragListener() {
                    @Override
                    public void showOffset(String offsetContent) {
                        board.updateInfo(offsetContent);
                    }
                });
                vContainer.addView(editAttrLayout);
                break;
            case TYPE_RELATIVE_POSITION:
                vContainer.addView(new RelativePositionLayout(this));
                break;
            case TYPE_SHOW_GRIDDING:
                vContainer.addView(new GriddingLayout(this));
                board.updateInfo("LINE_INTERVAL: " + DimenUtil.px2dip(GriddingLayout.LINE_INTERVAL, true));
                break;
            case TYPE_COMPOSE:
                UInspector.currentState.setRunning$UETool_Uinspector_api(!UInspector.currentState.isRunning());
                UInspector.INSTANCE.syncState();
                break;
            default:
                Toast.makeText(this, getString(R.string.uet_coming_soon), Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        params.gravity = BOTTOM;
        vContainer.addView(board, params);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UETool.getInstance().release();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    public void dismissAttrsDialog() {
        for (int i = 0; i < vContainer.getChildCount(); i++) {
            View child = vContainer.getChildAt(i);
            if (child instanceof EditAttrLayout) {
                ((EditAttrLayout) child).dismissAttrsDialog();
            }
        }
    }

    @IntDef({
            TYPE_UNKNOWN,
            TYPE_EDIT_ATTR,
            TYPE_SHOW_GRIDDING,
            TYPE_RELATIVE_POSITION,
            TYPE_COMPOSE,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
        int TYPE_UNKNOWN = -1;
        int TYPE_EDIT_ATTR = 1;
        int TYPE_SHOW_GRIDDING = 2;
        int TYPE_RELATIVE_POSITION = 3;
        int TYPE_COMPOSE = 4;
    }
}
