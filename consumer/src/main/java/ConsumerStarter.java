import com.huahuo.rpc.model.User;
import com.huahuo.rpc.proxy.ServiceProxyFactory;
import com.huahuo.rpc.service.UserService;

public class ConsumerStarter {
    public static void main(String[] args) {
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        System.out.println(userService.getUser(new User("花火")));
    }
}
