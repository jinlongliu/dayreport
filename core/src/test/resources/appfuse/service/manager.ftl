package ${basepackage}.service;

import ${basepackage}.service.GenericManager;
import ${basepackage}.model.${pojo.shortName};

import java.util.List;
import javax.jws.WebService;

@WebService
public interface ${pojo.shortName}Manager extends GenericManager<${pojo.shortName}, ${pojo.getJavaTypeName(pojo.identifierProperty, jdk5)}> {
    
}