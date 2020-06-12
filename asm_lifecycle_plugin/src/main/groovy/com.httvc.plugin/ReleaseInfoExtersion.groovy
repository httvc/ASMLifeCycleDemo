/**
 * 与自定义Plugin进行参数传递
 */
class ReleaseInfoExtersion{
    String versionCode
    String versionName
    String versionInfo
    String fileName

    ReleaseInfoExtersion(){

    }

    @Override
    String toString() {
       return """|versionCode=${versionCode}
                 |versionName=${versionName}
                 |versionInfo=${versionInfo}
                 |fileName=${fileName}
              """
    }
}