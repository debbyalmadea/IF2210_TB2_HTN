package plugin.htn.piechartplugin;

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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PieChart implements Plugin, View, CustomerDataStoreExtension {
    private javafx.scene.chart.PieChart pieChart;
    private final BorderPane view;
    private List<Member> memberData = new ArrayList<>();
    private List<Customer> customerData = new ArrayList<>();
    private List<VIPMember> vipMemberData = new ArrayList<>();
    private ObservableList<javafx.scene.chart.PieChart.Data> pieChartData = FXCollections.observableArrayList();
    public PieChart() {
        view = new BorderPane();
        pieChart = new javafx.scene.chart.PieChart(pieChartData);
        view.setCenter(pieChart);
        init();
        BasePlugin.bindCustomerDataStore(this);
    }
    public void load() {
        BasePlugin.addPage("analytics pie", this.getClass());
    }
    public Node getView() {
        return this.view;
    }
    public void init() {
        System.out.println("Plugin!");
    }
    public StringProperty getTitle() {
        return new SimpleStringProperty("Analytics Pie");
    }
    public void onMemberDataStoreChange(@NotNull DataStore<Member> memberDataStore) {
        pieChart.getData().clear();
        memberData = memberDataStore.getData();
        graphData();
    }
    public void onCustomerDataStoreChange(@NotNull DataStore<Customer> customerDataStore) {
        pieChart.getData().clear();
        customerData = customerDataStore.getData();
        graphData();
    }
    public void onVIPMemberDataStoreChange(@NotNull DataStore<VIPMember> vipMemberDataStore) {
        pieChart.getData().clear();
        vipMemberData = vipMemberDataStore.getData();
        graphData();
    }
    private void graphData() {
        pieChartData.add(new javafx.scene.chart.PieChart.Data("Customer only", customerData.size() - memberData.size() - vipMemberData.size()));
        pieChartData.add(new javafx.scene.chart.PieChart.Data("Member only", memberData.size()));
        pieChartData.add(new javafx.scene.chart.PieChart.Data("VIP Member", vipMemberData.size()));
    }
}
