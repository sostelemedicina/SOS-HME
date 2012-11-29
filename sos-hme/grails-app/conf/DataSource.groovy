dataSource {
	pooled = true
	driverClassName = "org.hsqldb.jdbcDriver"
	username = "sa"
	password = ""
}
hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
	
        loadData{
            dataSource {
//          dbCreate = "create-drop" // one of 'create', 'create-drop','update'
//			url = "jdbc:hsqldb:mem:devDB"
			
	        pooling = true
	        driverClassName = "com.mysql.jdbc.Driver"
	        url = "jdbc:mysql://localhost:3306/oehr_dev?useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8"
	        dbCreate = "create"
	        username = "root"
	        password = "dogui01"
                //loggingSql = true
		}
        
        
        }
       
        development {
		dataSource {
//          dbCreate = "create-drop" // one of 'create', 'create-drop','update'
//			url = "jdbc:hsqldb:mem:devDB"
			
	        pooling = true
	        driverClassName = "com.mysql.jdbc.Driver"
	        url = "jdbc:mysql://localhost:3306/oehr_dev?useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8"
	        dbCreate = "update"
	         username = "root"
	        password = "dogui01"
                //loggingSql = true
		}
	}
	test {
		dataSource {
//          dbCreate = "create-drop" // one of 'create', 'create-drop','update'
//			url = "jdbc:hsqldb:mem:devDB"
			
	        pooling = true
	        driverClassName = "com.mysql.jdbc.Driver"
	        url = "jdbc:mysql://localhost:3306/oehr_dev?useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8"
	        dbCreate = "update"
	         username = "root"
	        password = "dogui01"
                //loggingSql = true
		}
	}
	production {
		dataSource {
//          dbCreate = "create-drop" // one of 'create', 'create-drop','update'
//			url = "jdbc:hsqldb:mem:devDB"
			
	        pooling = true
	        driverClassName = "com.mysql.jdbc.Driver"
	        url = "jdbc:mysql://localhost:3306/oehr_dev?useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8"
	        dbCreate = "update"
	        username = "root"
	        password = ""
                //loggingSql = true
		}
	}
}