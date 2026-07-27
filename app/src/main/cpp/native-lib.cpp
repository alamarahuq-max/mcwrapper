#include <jni.h>
#include <string>
#include <android/log.h>
#include <thread>
#include <atomic>

#define LOG_TAG "MCWrapper"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

static std::atomic<bool> serverRunning(false);

// Forward declaration
int minecraftServerMain();

extern "C" JNIEXPORT void JNICALL
Java_com_example_mcwrapper_ServerService_nativeStartServer(
        JNIEnv *env,
        jobject thiz) {
    LOGI("Native: Starting Minecraft Server");
    
    if (!serverRunning.exchange(true)) {
        // Start server in a separate thread
        std::thread serverThread([]() {
            try {
                LOGI("Native: Server thread started");
                // Call your actual Minecraft server code here
                // For now, we'll just run a simple loop
                while (serverRunning.load()) {
                    LOGI("Native: Server running...");
                    sleep(5);
                }
            } catch (const std::exception &e) {
                LOGE("Native: Exception in server thread: %s", e.what());
            }
        });
        serverThread.detach();
        LOGI("Native: Server started successfully");
    }
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_mcwrapper_ServerService_nativeStopServer(
        JNIEnv *env,
        jobject thiz) {
    LOGI("Native: Stopping Minecraft Server");
    serverRunning.store(false);
    LOGI("Native: Server stopped");
}
