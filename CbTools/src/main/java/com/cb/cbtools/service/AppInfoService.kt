package com.cb.cbtools.service

import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInstaller.SessionParams
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import com.cb.cbtools.dto.AppListInfo
import com.cb.cbtools.exception.SelfDestructionException
import com.cb.cbtools.exception.SystemAppException
import com.cb.cbtools.util.FileUtil
import java.io.File
import java.io.IOException
import javax.inject.Inject


class AppInfoService @Inject constructor(
    private val context: Context,
    private val packageManager: PackageManager
) {
    @SuppressLint("QueryPermissionsNeeded")
    fun getAppInfoListWithoutIconAndVersion(data: Set<String>): List<AppListInfo> {
        val appList: MutableList<AppListInfo> = ArrayList()
        val map = getAppWithLauncher()

        val packList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        for (packInfo in packList) {
            val pkgName = packInfo.packageName
            if (pkgName != context.packageName) {
                if (packInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                    appList.add(
                        AppListInfo(
                            pkg = pkgName,
                            name = packInfo.loadLabel(packageManager).toString(),
                            appType = 0,
                            sourceDir = packInfo.sourceDir,
                            isEnabled = data.contains(pkgName)
                        )
                    )
                } else if (packInfo.flags and ApplicationInfo.FLAG_SYSTEM == 1
                    && map.containsKey(pkgName)
                ) {
                    appList.add(
                        AppListInfo(
                            pkg = pkgName,
                            name = packInfo.loadLabel(packageManager)
                                .toString(),
                            appType = 1,
                            sourceDir = packInfo.sourceDir,
                            isEnabled = data.contains(pkgName)
                        )
                    )
                }
            }
        }
        appList.sort()
        return appList
    }

    fun getAppInfoList(data: Set<String>): List<AppListInfo> {
        val appList: MutableList<AppListInfo> = ArrayList()
        val map = getAppWithLauncher()
        val packList = packageManager.getInstalledPackages(0)
        for (packInfo in packList) {
            if (packInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                val pkgName = packInfo.applicationInfo.packageName
                if (pkgName != context.packageName) {
                    appList.add(
                        AppListInfo(
                            pkg = pkgName,
                            name = packInfo.applicationInfo.loadLabel(packageManager).toString(),
                            icon = packInfo.applicationInfo.loadIcon(packageManager),
                            appType = 0,
                            version = packInfo.versionName,
                            sourceDir = packInfo.applicationInfo.sourceDir,
                            isEnabled = data.contains(pkgName)
                        )
                    )
                }
            } else if (packInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 1) {
                val pkgName = packInfo.applicationInfo.packageName
                if (pkgName != context.packageName) {
                    if (map.containsKey(pkgName)) {
                        appList.add(
                            AppListInfo(
                                pkg = pkgName,
                                name = packInfo.applicationInfo.loadLabel(packageManager)
                                    .toString(),
                                icon = packInfo.applicationInfo.loadIcon(packageManager),
                                appType = 1,
                                version = packInfo.versionName,
                                sourceDir = packInfo.applicationInfo.sourceDir,
                                isEnabled = data.contains(pkgName)
                            )
                        )
                    }
                }
            }
        }
        appList.sort()
        return appList
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun getAppWithLauncher(): MutableMap<String, File> {
        val map: MutableMap<String, File> = HashMap()
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val apps = packageManager.queryIntentActivities(mainIntent, 0)
        for (info in apps) {
            val file = File(info.activityInfo.applicationInfo.publicSourceDir)
            map[info.activityInfo.packageName] = file
        }
        return map
    }

    fun appNameLookup(packageName: String?): String? {
        return try {
            packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName!!, 0))
                .toString()
        } catch (e: PackageManager.NameNotFoundException) {
            null
        } catch (e: NullPointerException) {
            null
        }
    }

    fun appIconLookup(packageName: String?): Drawable? {
        return try {
            packageManager.getApplicationIcon(packageManager.getApplicationInfo(packageName!!, 0))
        } catch (e: PackageManager.NameNotFoundException) {
            null
        } catch (e: NullPointerException) {
            null
        }
    }

    fun isPackageInstalled(packageName: String): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        } catch (e: NullPointerException) {
            false
        }
    }


    @RequiresApi(Build.VERSION_CODES.P)
    fun getAppVersion(): Long {
        return try {
            packageManager.getPackageInfo(
                context.packageName,
                0
            ).longVersionCode
        } catch (e: Exception) {
            0
        }
    }

     fun extract(
         extractTo: String,
         app: AppListInfo,
         onSuccess: () -> Unit,
         onFailure: (Exception) -> Unit
     ) {
         FileUtil.copyFile(
             app.sourceDir!!,
             Environment.getExternalStoragePublicDirectory(extractTo).absolutePath,
             app.name + ".apk",
             onSuccess,
            onFailure
        )
    }

    fun uninstall(
        activity: Activity,
        app: AppListInfo,
        onFailure: (Exception) -> Unit
    ) {
        if (app.appType == 0) {
            try {
                if (app.pkg == activity.packageName) {
                    onFailure(SelfDestructionException("Are you kidding me?"))
                } else {
                    uninstallPackageWithIntent(activity, app.pkg)
                }
            } catch (e: Exception) {
                onFailure(e)
            }
        } else {
            onFailure(SystemAppException("This is a system app, Please install our desktop app to uninstall this"))
        }
    }

    private fun uninstallPackageWithIntent(activity: Activity, packageName: String?) {
        val intent = Intent(Intent.ACTION_DELETE)
        intent.data = Uri.parse("package:$packageName")
        activity.startActivity(intent)
    }

    @SuppressLint("MissingPermission")
    fun uninstallPackage(context: Context, packageName: String?): Boolean {
        val packageManger = context.packageManager
        val packageInstaller = packageManger.packageInstaller
        val params = SessionParams(
            SessionParams.MODE_FULL_INSTALL
        )
        params.setAppPackageName(packageName)
        val sessionId: Int = try {
            packageInstaller.createSession(params)
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
        packageInstaller.uninstall(
            packageName!!, PendingIntent.getBroadcast(
                context, sessionId,
                Intent("android.intent.action.MAIN"),
                PendingIntent.FLAG_IMMUTABLE
            ).intentSender
        )
        return true
    }

}