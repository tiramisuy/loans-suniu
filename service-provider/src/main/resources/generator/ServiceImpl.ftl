package ${targetPackage};

import org.springframework.stereotype.Service;

import ${tableClass.packageName}.${tableClass.shortClassName};
import ${servicePackage}.${tableClass.shortClassName}${serviceSuffix};
import ${daoPackage}.${tableClass.shortClassName}${daoSuffix};
import com.rongdu.common.service.MyBaseServiceImpl;
<#assign dateTime = .now>
/**
 * @author yuanxianchu
 * @date ${dateTime?string["yyyy-MM-dd HH:mm:ss"]}
 */
@Service("${tableClass.variableName}${serviceSuffix}")
public class ${tableClass.shortClassName}${serviceSuffix}Impl extends MyBaseServiceImpl<${tableClass.shortClassName},${tableClass.shortClassName}${daoSuffix}>
		implements ${tableClass.shortClassName}${serviceSuffix}{

}