// Write C++ code here.
//
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("myandroid");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("myandroid")
//      }
//    }

#include <jni.h>
#include "lexer.h"
#include "parser.h"


JNIEXPORT jstring JNICALL
Java_com_example_myandroid_ui_calculator_CalculatorViewModel_getMessage(
        JNIEnv *env, jobject thiz, jstring jName
) {

    const int size = (*env)->GetStringUTFLength(env, jName);
    char *name = (*env)->GetStringUTFChars(env, jName, NULL);

    for(int i = 0; i < size / 2; ++i) {
        char tmp = name[i];
        name[i] = name[size - i - 1];
        name[size - i - 1] = tmp;
    }

    return (*env)->NewStringUTF(env, name);
}


JNIEXPORT jint JNICALL
Java_com_example_myandroid_ui_calculator_CalculatorViewModel_calculate(JNIEnv *env, jobject thiz,
                                                                       jstring jExpr) {
    int ret, res;

    const char *expr = (*env)->GetStringUTFChars(env, jExpr, NULL);

    YY_BUFFER_STATE bp = yy_scan_string(expr);
    yy_switch_to_buffer(bp);
    ret = yyparse(&res);
    yy_delete_buffer(bp);

    return res;
}