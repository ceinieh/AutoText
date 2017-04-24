package com.example.pocdynamictext.cards;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.telecom.GatewayInfo;
import android.text.InputFilter;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pocdynamictext.R;
import com.example.pocdynamictext.cards.pager.AlignmentFragment;
import com.example.pocdynamictext.cards.pager.FontSizesFragment;
import com.example.pocdynamictext.cards.pager.FontsFragment;
import com.example.pocdynamictext.cards.pager.PagesAdapter;
import com.example.pocdynamictext.views.AutoFitEditText;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener,
        FontsFragment.OnFontChangeListener, FontSizesFragment.OnFontSizeChangeListener,
        AlignmentFragment.InterfaceFontAlignment{

    private TabLayout mTabLayout;
    private AutoFitEditText mAutoFitEditText;
    private ViewPager viewPager;
    private TextView message_text;
    private int maxEditTextSize = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        message_text = (TextView) findViewById(R.id.message_label);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initViews();
        initTabs();
        initViewPager();
        initAutoFitEditText();
    }

    private void initAutoFitEditText() {
        mAutoFitEditText.setEnabled(true);
        mAutoFitEditText.setFocusableInTouchMode(true);
        mAutoFitEditText.setFocusable(true);
        mAutoFitEditText.setEnableSizeCache(false);
        mAutoFitEditText.setMovementMethod(null);
        mAutoFitEditText.setMaxHeight(220);
        mAutoFitEditText.setMinTextSize(60f);

        mAutoFitEditText.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(maxEditTextSize ) // 150 is max length
        });

        mAutoFitEditText.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(mAutoFitEditText.isFirstLimitReached())
                    message_text.setText("Font size adjusted to fit text box");

                if(mAutoFitEditText.getText().length() == maxEditTextSize ) {
                    message_text.setText("Too much text for this section");
                    mAutoFitEditText.setFirstLimit(false);
                }
                else mAutoFitEditText.setFirstLimit(true);


                return false;
            }
        });



    }

    private void initViews() {
        mTabLayout = (TabLayout) findViewById(R.id.tabs_layout);
        mAutoFitEditText = (AutoFitEditText) findViewById(R.id.rET);
        viewPager = (ViewPager) findViewById(R.id.lists_pager);
    }

    private void initTabs() {
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_font_family));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_font_size));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_font_alignment));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_font_color));
        mTabLayout.addOnTabSelectedListener(MainActivity.this);
    }

    private void initViewPager() {
        final PagesAdapter adapter = new PagesAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onFontSelected(Typeface font) {
        mAutoFitEditText.setTypeface(font);
    }

    @Override
    public void onFontSizeSelected(int fontSize) {
        mAutoFitEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
    }

    @Override
    public void onFontAlignmentPass(int data) {
        switch (data){

            case 0:
                mAutoFitEditText.setGravity(Gravity.CENTER_VERTICAL);

                break;
            case 1:
                mAutoFitEditText.setGravity(Gravity.LEFT);
                break;
            case 2:
                mAutoFitEditText.setGravity(Gravity.RIGHT);
                break;
            case 3:
                mAutoFitEditText.setGravity(Gravity.CENTER | Gravity.BOTTOM);
                break;
            case 4:
                mAutoFitEditText.setGravity(Gravity.CENTER_VERTICAL  | Gravity.CENTER_HORIZONTAL);
                break;
            case 5:
                mAutoFitEditText.setGravity(Gravity.TOP | Gravity.CENTER);
                break;
        }
    }
}
