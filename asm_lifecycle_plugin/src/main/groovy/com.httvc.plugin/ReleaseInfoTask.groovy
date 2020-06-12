import groovy.xml.MarkupBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * 自定义Task,实现维护版本信息功能
 */
class ReleaseInfoTask extends DefaultTask{
    ReleaseInfoTask(){
        group='httvc'
        description='莎啦啦是登录大'
    }

    /**
     * 用TaskAction在整个执行阶段执行
     * 执行于gralde执行阶段代码
     */
    @TaskAction
    void doAction(){
        updateInfo()
    }

    //真正的将Extension类中的信息，写入指定文件中
    private void updateInfo(){
        //获取将要写入的信息
        String versionCodeMsg=project.extensions.httvcReleaseInfo.versionCode
        String versionNameMsg=project.extensions.httvcReleaseInfo.versionName
        String versionInfoMsg=project.extensions.httvcReleaseInfo.versionInfo
        String fileName=project.extensions.httvcReleaseInfo.fileName
        def file=project.file(fileName)
        //将实体对象写入到xml文件中
        def sw=new StringWriter()
        def xmlBuilder=new MarkupBuilder(sw)
        if (file.text!=null && file.text.size()<=0){
            //没有内容
            xmlBuilder.release{
                release{
                    versionCode(versionCodeMsg)
                    versionName(versionNameMsg)
                    versionInfo(versionInfoMsg)
                }
            }
            //直接写入
            file.withWriter {writer->writer.append(sw.toString())}
        }else {
            //已有其它版本内容
            xmlBuilder.release{
                versionCode(versionCodeMsg)
                versionName(versionNameMsg)
                versionInfo(versionInfoMsg)
            }
            //插入到最后一行前面
            def lines=file.readLines()
            def lengths=lines.size()-1
            file.withWriter {writer->
                lines.eachWithIndex{ line, index ->
                    if (index!=lengths){
                        writer.append(line+'\r\n')
                    }else if (index==lengths){
                        writer.append('\r\r\n'+sw.toString() +'\r\n')
                        writer.append(lines.get(lengths))
                    }
                }
            }
        }

     }
}