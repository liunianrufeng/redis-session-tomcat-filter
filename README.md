# redis-session-tomcat-filter
基于redis、tomcat filter的tomcat集群session共享实现<br/>
在工程下新建sessionparameter.properties文件<br/>
具体配置参考sessionparameter_default.properties<br/>
如果配置了redis_cluster.host，则redis.pool.host的配置自动失效<br/>

对jedis做了简单修改，需要把pom文件中关于jedis的依赖注释调<br/>
直接引用lib中的jedis-3.0.0-SNAPSHOT.jar<br/>


