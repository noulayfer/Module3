import com.fedorenko.action.Actions;
import com.fedorenko.service.Service;
import com.fedorenko.util.UserInput;
import org.flywaydb.core.Flyway;

public class Main {
    @SuppressWarnings("all")
    public static void main(String[] args) {
        final Service service = Service.getInstance();
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/test", "postgres", "1237d7321")
                .cleanDisabled(false)
                .baselineOnMigrate(true)
                .locations("filesystem:src/main/resources/db/migration")
                .load();
        flyway.clean();
        flyway.migrate();

        while(true) {
            final String[] strings = Actions.mapToNames();
            final int menu = UserInput.menu(strings);
            Actions.values()[menu].execute();
        }
    }
}
