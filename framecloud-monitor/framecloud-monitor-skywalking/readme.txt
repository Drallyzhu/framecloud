###1、先启动ES，修改skywalking配置，开启ES存储方式


###2、启动每个服务时加上以下虚拟机参数，Dskywalking.agent.service_name 对应服务名

-javaagent:/xxxxxx/framecloud-parent/framecloud-monitor/framecloud-monitor-skywalking/agent/skywalking-agent.jar
-Dskywalking.agent.service_name=framecloud-xxxx-service
-Dskywalking.collector.backend_service=localhost:11800