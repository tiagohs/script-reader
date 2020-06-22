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

-keep class com.shockwave.**

# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/AMarones/Library/Android/sdk/tools/proguard/proguard-android.txt
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

#=================================================================================================
## Kotlin
#=================================================================================================
-assumenosideeffects class kotlin.jvm.internal.Intrinsics { *; }
#=================================================================================================

#=================================================================================================
## Lambda
#=================================================================================================
-dontwarn java.lang.invoke**
#=================================================================================================

#=================================================================================================



#=================================================================================================
## Picasso
#=================================================================================================
-dontwarn com.squareup.okhttp.**
#=================================================================================================


#=================================================================================================
## Okio
#=================================================================================================
-dontwarn okio.**
#=================================================================================================


#=================================================================================================
## Retrofit
#=================================================================================================
# Retrofit 2.X
## https://square.github.io/retrofit/ ##

# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>
#=================================================================================================


#=================================================================================================
## Rx
#=================================================================================================
-dontwarn sun.misc.**

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-dontnote rx.internal.util.PlatformDependent
#=================================================================================================



#=================================================================================================
## Support Design
#=================================================================================================
-dontwarn android.support.design.**
#=================================================================================================


#=================================================================================================
## Junit
#=================================================================================================
-dontwarn android.test.**
#=================================================================================================


#=================================================================================================
## Okhttp3
#=================================================================================================
-dontwarn okhttp3.internal.**
-dontwarn okhttp3.logging.**
#=================================================================================================

#=================================================================================================
## Enum
#=================================================================================================
-keepclassmembers enum * { *; }
#=================================================================================================

#=================================================================================================
## RenderScript
#=================================================================================================
-dontwarn androidx.**
-keep class androidx.** { *; }
-keep interface androidx.** { *; }
-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclasseswithmembernames class * { @androidx.* <methods>; }
-keepclasseswithmembernames class * { @androidx.* <fields>; }
#=================================================================================================