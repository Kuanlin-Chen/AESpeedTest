LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := aes_jni
LOCAL_SRC_FILES := main.c aes/aes.c

include $(BUILD_SHARED_LIBRARY)