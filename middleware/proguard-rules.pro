#-keepattributes *Annotation*

#-keep class * implements java.io.Serializable { *; }

# Glide
#-keep public class * implements com.bumptech.glide.module.GlideModule
#-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  #**[] $VALUES;
 # public *;
#}

#-ignorewarnings