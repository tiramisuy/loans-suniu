package ${targetPackage};

import ${tableClass.packageName}.${tableClass.shortClassName};
import com.rongdu.common.persistence.MyBaseService;

<#assign dateTime = .now>
/**
 * @author yuanxianchu
 * @date ${dateTime?string["yyyy-MM-dd HH:mm:ss"]}
 */
public interface ${tableClass.shortClassName}${serviceSuffix} extends MyBaseService<${tableClass.shortClassName}>{

}