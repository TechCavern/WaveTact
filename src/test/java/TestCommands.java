import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.thread.CommandCollection;
import org.junit.Test;

import java.util.List;

public final class TestCommands{
    @Test
    public void test()
    throws Exception{
        CommandCollection collection = new CommandCollection("com.techcavern.wavetact.commands");
        List<Command> cmds = collection.call();
        for(Command cmd : cmds){
            System.out.println(cmd.getClass().getName());
        }
    }
}