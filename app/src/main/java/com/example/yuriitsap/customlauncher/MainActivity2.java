package com.example.yuriitsap.customlauncher;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity2 extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);

        SpotlightView spotlight = (SpotlightView) findViewById(R.id.spotlight);
        spotlight.setAnimationSetupCallback(new SpotlightView.AnimationSetupCallback() {
            @Override
            public void onSetupAnimation(SpotlightView spotlight) {
                createAnimation(spotlight);
            }
        });
    }

    private void createAnimation(final SpotlightView spotlight) {
        spotlight.setMaskX(spotlight.getWidth()/2.0f);
        spotlight.setMaskY(spotlight.getHeight()/2.0f);

        spotlight.animate().alpha(1.0f).withLayer().withEndAction(new Runnable() {
            @Override
            public void run() {
                final ObjectAnimator superScale = ObjectAnimator.ofFloat(spotlight, "maskScale",
                        spotlight.computeMaskScale(
                                Math.max(spotlight.getHeight(), spotlight.getWidth()) * 1.7f));
                superScale.setDuration(2000);

                AnimatorSet set = new AnimatorSet();
                set.play(superScale);
                set.start();

                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        findViewById(R.id.content).setVisibility(View.VISIBLE);
                        findViewById(R.id.spotlight).setVisibility(View.GONE);
                        getWindow().setBackgroundDrawable(null);
                    }
                });
            }
        });
    }

}
