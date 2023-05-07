package plugin.htn.chartplugin;
import com.htn.api.datastore.CustomerDataStoreExtension;
import com.htn.api.datastore.DataStore;
import com.htn.api.view.View;
import com.htn.data.customer.Customer;
import com.htn.data.customer.Member;
import com.htn.data.customer.VIPMember;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BarChart implements View, CustomerDataStoreExtension {
    private javafx.scene.chart.BarChart<String, Number> barChart;
    private List<Member> memberData = new ArrayList<>();
    private List<Customer> customerData = new ArrayList<>();
    private List<VIPMember> vipMemberData = new ArrayList<>();
    private final Node view;
    public BarChart() {
        System.out.println("INIT BAR CHART");
        view = new BorderPane();
        init();
    }
    public Node getView() {
        return this.view;
    }
    public void init() {
        System.out.println("Plugin!");
        // Create the chart axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Member Name");
        yAxis.setLabel("Point");

        // Create the chart
        barChart = new javafx.scene.chart.BarChart<>(xAxis, yAxis);
        barChart.setTitle("Member Points");
        ((BorderPane)view).setCenter(barChart);
    }
    public StringProperty getTitle() {
        return new SimpleStringProperty("Analytics");
    }
    public void onMemberDataStoreChange(@NotNull DataStore<Member> memberDataStore) {
        System.out.println("MEMBER DATA STORE!");
        memberData = memberDataStore.getData();
        barChart.getData().clear();
        graphMemberData();
        graphVIPMemberData();
    }
    private void graphMemberData() {
        // Add the data to the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Member member : memberData) {
            series.getData().add(new XYChart.Data<>(member.getName(), member.getPoint()));
        }
        barChart.getData().add(series);
    }
    public void onCustomerDataStoreChange(@NotNull DataStore<Customer> customerDataStore) {
        customerData = customerDataStore.getData();
//        barChart.getData().clear();
//
//        // Add the data to the chart
//        XYChart.Series<String, Number> series = new XYChart.Series<>();
//        for (Customer customer : customerData) {
//            series.getData().add(new XYChart.Data<>(customer.getName(), member.getPoint()));
//        }
//        barChart.getData().add(series);
    }
    public void onVIPMemberDataStoreChange(@NotNull DataStore<VIPMember> vipMemberDataStore) {
        System.out.println("PLUGIN!");
        vipMemberData = vipMemberDataStore.getData();
        barChart.getData().clear();
        graphVIPMemberData();
        graphMemberData();
    }
    private void graphVIPMemberData() {
        // Add the data to the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (VIPMember vipMember : vipMemberData) {
            series.getData().add(new XYChart.Data<>(vipMember.getName(), vipMember.getPoint()));
        }
        barChart.getData().add(series);
    }
}