/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.transaksi.model;

import com.je.masterdata.model.Barang;
import com.je.masterdata.model.Customer;
import java.sql.Timestamp;

/**
 *
 * @author Newjar
 */
public class Penjualan {
    private String kode;
    private Customer customer;
    private Barang barang;
    private Timestamp tgl_penjualan;
    private Integer jumlah;
    private Integer harga_barang;
    private Integer sub_total_harga;

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }

    public Timestamp getTgl_penjualan() {
        return tgl_penjualan;
    }

    public void setTgl_penjualan(Timestamp tgl_penjualan) {
        this.tgl_penjualan = tgl_penjualan;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public Integer getHarga_barang() {
        return harga_barang;
    }

    public void setHarga_barang(Integer harga_barang) {
        this.harga_barang = harga_barang;
    }

    public Integer getSub_total_harga() {
        return sub_total_harga;
    }

    public void setSub_total_harga(Integer sub_total_harga) {
        this.sub_total_harga = sub_total_harga;
    }

    
}
