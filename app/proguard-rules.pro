# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Jack\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-optimizationpasses 5 #压缩级别
-dontusemixedcaseclassnames #大小写混合
-dontpreverify #混淆日志
-verbose #混淆日志

#混淆的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.app.BroadcastReceiver
-keep public class * extends android.app.ContentProvider
-keep public class * extends android.app.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keepclasseswithmembernames class *{#保持native方法不会被混淆
    native <methods>;
    }
-keepclasseswithmembers class *{#保持自定义属性不被混淆
    public <init>(android.content.Context,android.util.AttributeSet, int);
    }
-keepclassmembernames class * extends android.app.Activity{#保持自定义view不被混淆
    public void *(android.view.View);
    }
-keepclassmembers enum * {#防止enum类别混淆
    public static **[] values();
     public static ** valuesOf(java.lang.String);
    }
-keep class * implements android.os.Parcelable{#保持parcelable不被混淆
    public static final android.os.Parcelable$Creator *;
    }

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
