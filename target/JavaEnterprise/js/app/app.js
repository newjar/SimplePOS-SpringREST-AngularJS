(function(){
	
	var app = angular.module('je', ['ngRoute', 'datePicker', 'cgBusy', 'je-paging',
	                                 'je-main-workspace-controller',
                                         'je-masterdata-barang-controller',
                                         'je-masterdata-customer-controller',
                                         'je-masterdata-supplier-controller',
                                         'je-transaksi-penjualan-controller',
                                         'je-transaksi-pembelian-controller',
	                                 'angularUtils.directives.dirPagination']);

	app.config(function($routeProvider) {
		$routeProvider
		.when("/", {
			templateUrl : "template/main/dashboard.html"
		})
                //BARANG
                .when("/masterdata/barang", {
			templateUrl : "template/masterdata/dataBarang.html",
			controller: "BarangListController"
		})
                .when("/masterdata/barang/create", {
			templateUrl : "template/masterdata/tambahBarang.html",
			controller: "BarangCreateController"
		})
		.when("/masterdata/barang/:kode/edit", {
			templateUrl : "template/masterdata/tambahBarang.html",
			controller: "BarangEditController"
		})
		.when("/masterdata/barang/:kode/detail", {
			templateUrl : "template/masterdata/detailBarang.html",
			controller: "BarangDetailController"
		})
                //CUSTOMER
                .when("/masterdata/customer", {
			templateUrl : "template/masterdata/dataCustomer.html",
			controller: "CustomerListController"
		})
                .when("/masterdata/customer/create", {
			templateUrl : "template/masterdata/tambahCustomer.html",
			controller: "CustomerCreateController"
		})
		.when("/masterdata/customer/:kode/edit", {
			templateUrl : "template/masterdata/tambahCustomer.html",
			controller: "CustomerEditController"
		})
		.when("/masterdata/customer/:kode/detail", {
			templateUrl : "template/masterdata/detailCustomer.html",
			controller: "CustomerDetailController"
		})
                //SUPPLIER
                .when("/masterdata/supplier", {
			templateUrl : "template/masterdata/dataSupplier.html",
			controller: "SupplierListController"
		})
                .when("/masterdata/supplier/create", {
			templateUrl : "template/masterdata/tambahSupplier.html",
			controller: "SupplierCreateController"
		})
		.when("/masterdata/supplier/:kode/edit", {
			templateUrl : "template/masterdata/tambahSupplier.html",
			controller: "SupplierEditController"
		})
		.when("/masterdata/supplier/:kode/detail", {
			templateUrl : "template/masterdata/detailSupplier.html",
			controller: "SupplierDetailController"
		})
                //PENJUALAN
                .when("/transaksi/penjualan", {
			templateUrl : "template/transaksi/dataPenjualan.html",
			controller: "PenjualanListController"
		})
                .when("/transaksi/penjualan/:kode/detail", {
			templateUrl : "template/transaksi/detailPenjualan.html",
			controller: "PenjualanDetailController"
		})
                .when("/transaksi/penjualan/create", {
			templateUrl : "template/transaksi/tambahPenjualan.html",
			controller: "PenjualanCreateController"
		})
                .when("/transaksi/penjualan/:kode/edit", {
			templateUrl : "template/transaksi/ubahPenjualan.html",
			controller: "PenjualanEditController"
		})
                //PEMBELIAN
                .when("/transaksi/pembelian", {
			templateUrl : "template/transaksi/dataPembelian.html",
			controller: "PembelianListController"
		})
                .when("/transaksi/pembelian/:kode/detail", {
			templateUrl : "template/transaksi/detailPembelian.html",
			controller: "PembelianDetailController"
		})
                .when("/transaksi/pembelian/create", {
			templateUrl : "template/transaksi/tambahPembelian.html",
			controller: "PembelianCreateController"
		})
                .when("/transaksi/pembelian/:kode/edit", {
			templateUrl : "template/transaksi/ubahPembelian.html",
			controller: "PembelianEditController"
		})
		;
	});
	
	app.filter('trusted', ['$sce', function ($sce) {
	    return function(url) {
	        return $sce.trustAsResourceUrl(url);
	    };
	}]);
	
})();



