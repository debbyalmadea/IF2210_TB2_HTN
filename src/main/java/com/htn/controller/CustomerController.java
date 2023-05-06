package com.htn.controller;

import com.htn.data.customer.Customer;
import com.htn.data.customer.Member;
import com.htn.data.customer.VIPMember;
import com.htn.datastore.customer.CustomerDataStore;
import com.htn.datastore.customer.MemberDataStore;
import com.htn.datastore.customer.VIPMemberDataStore;
import com.htn.view.View;
import javafx.collections.ListChangeListener;
import org.jetbrains.annotations.NotNull;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerController {
    public static void bindCustomerData(View view) {
        CustomerDataStore customerData = CustomerDataStore.getInstance();
        customerData.getCustomers().addListener((ListChangeListener<Customer>) c -> view.init());
    }
    public static void bindMemberData(View view) {
        MemberDataStore memberData = MemberDataStore.getInstance();
        memberData.getMembers().addListener((ListChangeListener<Customer>) c -> view.init());
    }
    public static void bindVIPMemberData(View view) {
        VIPMemberDataStore vipMemberData = VIPMemberDataStore.getInstance();
        vipMemberData.getVipMembers().addListener((ListChangeListener<Customer>) c -> view.init());
    }
    public static List<Customer> getAllCustomers() {
        return CustomerDataStore.getInstance().getCustomers();
    }
    public static List<Customer> getCustomersOnly() {
        return getAllCustomers().stream()
                .filter(customer -> getMember(customer.getId()) == null)
                .filter(customer -> getVIPMember(customer.getId()) == null)
                .collect(Collectors.toList());
    }
    public static @NotNull List<Member> getAllMembers() {
        return MemberDataStore.getInstance().getMembers();
    }
    public static Member getMember(Integer id) {
        return getAllMembers().stream()
                .filter(member -> member.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static ArrayList<String> getAllMemberName() {
        ArrayList<String> arr = new ArrayList<String>();
        getAllMembers().forEach(e-> {
            arr.add(e.getName());
        });
        getAllVIPMembers().forEach(e-> {
            arr.add(e.getName());
        });
        return arr;
    }

    public static Member getMemberByName(String name) {
        Member member = null;
        Optional<Member> memberReg = getAllMembers().stream().filter(e->e.getName().equalsIgnoreCase(name)).findFirst();
        Optional<VIPMember> memberVIP = getAllVIPMembers().stream().filter(e->e.getName().equalsIgnoreCase(name)).findFirst();
        if (memberReg.isPresent()) {
            member = memberReg.get();
        }
        if (memberVIP.isPresent()) {
            member = memberVIP.get();
        }
        return member;
    }

    public static List<Member> getActiveMembers() {
        return MemberDataStore.getInstance().getMembers().stream()
                .filter(Member::isActivated)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public static List<Member> getDeactivateMembers() {
        return MemberDataStore.getInstance().getMembers()
                .stream()
                .filter(member -> !member.isActivated())
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public static @NotNull List<VIPMember> getAllVIPMembers() {
        return VIPMemberDataStore.getInstance().getVipMembers();
    }
    public static VIPMember getVIPMember(Integer id) {
        return getAllVIPMembers().stream()
                .filter(VIPMember -> VIPMember.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    public static List<VIPMember> getActiveVIPMembers() {
        return VIPMemberDataStore.getInstance().getVipMembers().stream()
                .filter(VIPMember::isActivated)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public static List<VIPMember> getDeactivateVIPMembers() {
        return VIPMemberDataStore.getInstance().getVipMembers()
                .stream()
                .filter(vipMember -> !vipMember.isActivated())
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public static void create(Customer customer, String name, String phoneNumber, @NotNull String status) {
        if (status.equalsIgnoreCase("member")) {
            Member newMember = new Member(customer.getId(), name, phoneNumber, 20);
            MemberDataStore.getInstance().create(newMember);
        } else if (status.equalsIgnoreCase("vip")) {
            VIPMember newVIPMember = new VIPMember(customer.getId(), name, phoneNumber, 20);
            VIPMemberDataStore.getInstance().create(newVIPMember);
        }
    }
    public static void update(@NotNull Member member, String name, String phoneNumber, String status) {
        if (member instanceof VIPMember) {
            VIPMemberDataStore.getInstance().updateBuilder().vipMember((VIPMember) member).name(name).phoneNumber(phoneNumber).build();
            if (status.equalsIgnoreCase("member")) toMember((VIPMember) member);
        } else {
            MemberDataStore.getInstance().updateBuilder().member(member).name(name).phoneNumber(phoneNumber).build();
            if (status.equalsIgnoreCase("VIP")) toVIPMember(member);
        }
    }
    private static void toVIPMember(@NotNull Member member) {
        MemberDataStore.getInstance().delete(member);
        VIPMemberDataStore.getInstance().create(member);
    }
    private static void toMember(@NotNull VIPMember vipMember) {
        VIPMemberDataStore.getInstance().delete(vipMember);
        MemberDataStore.getInstance().create(vipMember);
    }
    public static void deactivate(@NotNull Member member) {
        if (member instanceof VIPMember) {
            VIPMemberDataStore.getInstance().updateBuilder()
                    .vipMember((VIPMember) member)
                    .activated(false).build();
        } else {
            MemberDataStore.getInstance().updateBuilder()
                    .member(member)
                    .activated(false).build();
        }
    }
    public static void activate(@NotNull Member member) {
        if (member instanceof VIPMember) {
            VIPMemberDataStore.getInstance().updateBuilder()
                    .vipMember((VIPMember) member)
                    .activated(true).build();
        } else {
            MemberDataStore.getInstance().updateBuilder()
                    .member(member)
                    .activated(true).build();
        }
    }
}
