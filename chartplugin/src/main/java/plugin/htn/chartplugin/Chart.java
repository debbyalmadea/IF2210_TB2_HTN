package plugin.htn.chartplugin;

import com.htn.api.BasePlugin;
import com.htn.api.Plugin;
import com.htn.api.datastore.CustomerDataStoreExtension;
import com.htn.api.datastore.DataStore;
import com.htn.api.view.View;
import com.htn.data.customer.Customer;
import com.htn.data.customer.Member;
import com.htn.data.customer.VIPMember;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

public class Chart implements Plugin, View, CustomerDataStoreExtension {
    private BarChart barChart;
    private LineChart lineChart;
    private final ScrollPane view;
    private final VBox content;
    public Chart() {
        view = new ScrollPane();
        view.fitToWidthProperty().set(true);
        content = new VBox();
        view.setContent(content);
        init();
        BasePlugin.bindCustomerDataStore(this);
    }
    public void load() {
        BasePlugin.addPage("analytics", this.getClass());
    }
    public Node getView() {
        return this.view;
    }
    public void init() {
        System.out.println("Plugin!");
        barChart = new BarChart();
        lineChart = new LineChart();
        content.getChildren().add(barChart.getView());
        content.getChildren().add(lineChart.getView());
    }
    public StringProperty getTitle() {
        return new SimpleStringProperty("Analytics");
    }
    public void onMemberDataStoreChange(@NotNull DataStore<Member> memberDataStore) {
        System.out.println("MEMBER DATA STORE!");
        barChart.onMemberDataStoreChange(memberDataStore);
        lineChart.onMemberDataStoreChange(memberDataStore);
    }
    public void onCustomerDataStoreChange(@NotNull DataStore<Customer> customerDataStore) {
    }
    public void onVIPMemberDataStoreChange(@NotNull DataStore<VIPMember> vipMemberDataStore) {
        System.out.println("PLUGIN!");
//        vipMemberData = vipMemberDataStore.getData();
        barChart.onVIPMemberDataStoreChange(vipMemberDataStore);
        lineChart.onVIPMemberDataStoreChange(vipMemberDataStore);
    }
}
