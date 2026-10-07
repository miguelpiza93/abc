package developers.apus.abecedario;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Android 15+ draws apps targeting API 35+ edge-to-edge. The layouts were designed to sit
 * between the system bars, so pad each screen's content by the system bar insets.
 */
public class AbcApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            registerActivityLifecycleCallbacks(new SystemBarsInsetsCallbacks());
        }
    }

    private static class SystemBarsInsetsCallbacks implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            if (!activity.getClass().getName().startsWith("developers.apus.")) {
                return;
            }
            // The bars now sit over the light window background, so use dark status bar icons.
            WindowCompat.getInsetsController(activity.getWindow(), activity.getWindow().getDecorView())
                    .setAppearanceLightStatusBars(true);
            View content = activity.getWindow().getDecorView().findViewById(android.R.id.content);
            ViewCompat.setOnApplyWindowInsetsListener(content, (v, windowInsets) -> {
                Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()
                        | WindowInsetsCompat.Type.displayCutout());
                v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
                return WindowInsetsCompat.CONSUMED;
            });
        }

        @Override public void onActivityStarted(Activity activity) {}
        @Override public void onActivityResumed(Activity activity) {}
        @Override public void onActivityPaused(Activity activity) {}
        @Override public void onActivityStopped(Activity activity) {}
        @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}
        @Override public void onActivityDestroyed(Activity activity) {}
    }
}
