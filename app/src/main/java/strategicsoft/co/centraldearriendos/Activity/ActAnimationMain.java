package strategicsoft.co.centraldearriendos.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;
import com.github.paolorotolo.appintro.AppIntro;

import strategicsoft.co.centraldearriendos.Fragment.SampleSlide;
import strategicsoft.co.centraldearriendos.R;

public class ActAnimationMain extends AppIntro {
    @Override
    public void init(Bundle savedInstanceState) {

        addSlide(SampleSlide.newInstance(R.layout.intro));
        //addSlide(SampleSlide.newInstance(R.layout.intro2));
        //addSlide(SampleSlide.newInstance(R.layout.intro3));

        setCustomTransformer(new ZoomOutPageTransformer());

    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, ActMain.class);
        startActivity(intent);
    }

    @Override
    public void onSkipPressed() {
        loadMainActivity();
        Toast.makeText(getApplicationContext(), getString(R.string.skip), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDonePressed() {
        loadMainActivity();
    }

    public void getStarted(View v){
        loadMainActivity();
    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}