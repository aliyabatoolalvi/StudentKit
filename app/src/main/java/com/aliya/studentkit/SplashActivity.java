package com.aliya.studentkit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private View advanceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        advanceView = findViewById(R.id.advance);

        if (advanceView != null) {
            advanceView.setOnClickListener(v -> {
                // Start the animation
                Animation slideOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
                advanceView.startAnimation(slideOut);

                // Set an animation listener to navigate to the next activity when the animation ends
                slideOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // Navigate to the next activity
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish(); // Optional: call finish() if you don't want to return to this activity
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
            });

            // Optional: Set up a gesture detector for sliding
            advanceView.setOnTouchListener(new OnSwipeTouchListener(this) {
                public void onSwipeLeft() {
                    // Start the animation
                    Animation slideOut = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.slide_out_right);
                    advanceView.startAnimation(slideOut);

                    // Set an animation listener to navigate to the next activity when the animation ends
                    slideOut.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            // Navigate to the next activity
                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish(); // Optional: call finish() if you don't want to return to this activity
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                }
            });
        } else {
            throw new RuntimeException("View with ID 'advance' not found in the layout");
        }
    }
}
