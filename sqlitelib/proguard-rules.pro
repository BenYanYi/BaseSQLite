# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keep class com.benyanyi.sqlitelib.*
-keep class com.benyanyi.sqlitelib.annotation.*
-keep class com.benyanyi.sqlitelib.exception.*
-keep class com.benyanyi.sqlitelib.condition.*
-keep class com.benyanyi.sqlitelib.config.*
-keep class com.benyanyi.sqlitelib.impl.*

-keepattributes *Annotation*

#-keepclassmembers class com.benyanyi.sqlitelib.*{*;}
-keepclassmembers class com.benyanyi.sqlitelib.annotation.*{
*;
}
-keepclassmembers class com.benyanyi.sqlitelib.condition.*{*;}
-keepclassmembers class com.benyanyi.sqlitelib.config.*{*;}
-keepclassmembers class com.benyanyi.sqlitelib.impl.*{*;}

-keepclassmembers class com.benyanyi.sqlitelib.TableDao.*{*;}
-keepclassmembers class com.benyanyi.sqlitelib.TableSession.*{*;}
# 内部类不会被混淆
-keepattributes SourceFile,LineNumberTable,InnerClasses

#"$"的含义是保留某类的内部类不会被混淆
-keepclassmembers class com.benyanyi.sqlitelib.TableDao$Builder {
     public <methods>;
}

-keepattributes Signature

-keepclassmembers enum * {
      public static **[] values();
      public static ** valueOf(java.lang.String);
}