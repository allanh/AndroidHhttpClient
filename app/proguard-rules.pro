-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable,InnerClasses

-keepattributes *Annotation*

-keep class * implements java.io.Serializable { *; }

-keep class android.support.v4.app.** { *; }
-keep class android.content.pm.** { *; }

# Keeps important Component
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider


# keeps all the Views
-keep public class * extends android.view.View {
  public <init>(android.content.Context);
  public <init>(android.content.Context, android.util.AttributeSet);
  public <init>(android.content.Context, android.util.AttributeSet, int);
  public void set*(...);
}

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-ignorewarnings