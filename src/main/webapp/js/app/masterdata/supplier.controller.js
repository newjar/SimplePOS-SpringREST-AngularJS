(function() {

	var app = angular.module('je-masterdata-supplier-controller', [
			'je-masterdata-supplier-service',
			'ui.bootstrap', 'dialogs' ]);

	app.controller('SupplierListController', [ '$scope', '$location',
			'$dialogs', 'SupplierListFactory','SupplierDeleteFactory',
			function($scope, $location, $dialogs, SupplierListFactory,SupplierDeleteFactory) {

				$scope.currentPage = 1;
				$scope.totalSupplier = 0;
				$scope.pageSize = 10;

				$scope.pagination = {
					current : 1
				};

				//ordering
				$scope.predicate='nama';
                                $scope.reverse=false;
	            
				$scope.pageChanged = function(newPage) {
					$scope.gridPromise = SupplierListFactory.query({
						activePage : newPage,
						order : $scope.predicate + "-" + ($scope.reverse ? "desc" : "asc")
					}, function(data) {
						$scope.supplierList = data.list;
						notif($dialogs, data.status, 'List');
						$scope.totalSupplier = data.total;
					});
				};
				$scope.pageChanged(1);

				$scope.search = function() {
					$scope.currentPage = 1;
					$scope.pageChanged(1);
				};

				$scope.create = function() {
					$location.path('/masterdata/supplier/create');
				};
				
			   $scope.delete = function (supplier) {
		        	dlg = $dialogs.confirm('Konfirmasi','Apakah anda ingin hapus Supplier : ' + supplier.nama);
		        	dlg.result.then(function(btn){
		        		SupplierDeleteFactory.delete({kode: supplier.kode}, function(data){
		        			notif($dialogs, data.status, 'Hapus');
		            		$scope.search();
		    			});
		            	
		            },function(btn){
		              //canceled
		            });
		            
		        };
		        
                        $scope.edit = function (supplier) {
                            $location.path('/masterdata/supplier/' + supplier.kode + '/edit');
		        };
		        
		        $scope.detail = function (supplier) {
		            $location.path('/masterdata/supplier/' + supplier.kode + '/detail');
		        };

			} ]);
                    
        app.controller('SupplierDetailController', [
                        '$scope',
                        '$routeParams',
                        '$location',
                        'SupplierEditFactory',
                        function($scope, $routeParams, $location, SupplierEditFactory) {

		$scope.title = "Info Detail Supplier";

		SupplierEditFactory.show({
			kode : $routeParams.kode
		}, function(data) {
			$scope.supplier = data.supplier;
		});


		$scope.cancel = function() {
			$location.path('/masterdata/supplier');
		};

	} ]);

        app.controller('SupplierCreateController', [
                '$scope',
                '$routeParams',
                '$location',
                '$dialogs',
                'SupplierCreateFactory',
                function($scope, $routeParams, $location,$dialogs,
                                SupplierCreateFactory) {

                        $scope.title = "Buat Baru Supplier";
                        $scope.isEdit = false;

                        $scope.supplier = {

                        };
                        $scope.save = function() {
                                SupplierCreateFactory.create({
                                        supplier : $scope.supplier
                                }, function(data) {
                                        notif($dialogs, data.status, 'Simpan');
                                        $location.path('/masterdata/supplier');
                                });

                        };

                        $scope.cancel = function() {
                                $location.path('/masterdata/supplier');
                        };

                } ]);

	app.controller('SupplierEditController', [
                '$scope',
                '$routeParams',
                '$location',
                '$dialogs',
                'SupplierEditFactory',
                function($scope, $routeParams, $location, $dialogs,SupplierEditFactory) {

                        $scope.title = "Ubah Supplier";
                        $scope.isEdit = true;

                        SupplierEditFactory.show({
                                kode : $routeParams.kode
                        }, function(data) {
                                $scope.supplier = data.supplier;
                        });

                        $scope.save = function() {
                                SupplierEditFactory.update({
                                        kode : $scope.supplier.kode,
                                        supplier : $scope.supplier
                                }, function(data) {
                                        notif($dialogs, data.status, 'Simpan');
                                        $location.path('/masterdata/supplier');
                                });

                        };

                        $scope.cancel = function() {
                                $location.path('/masterdata/supplier');
                        };

                } ]);
	
	function notif($dialogs, status, result) {

		switch (status) {
		case 'OK':
			if (result == 'Simpan') {
				dlg = $dialogs.notify('Informasi', 'Data Sukses Disimpan');
			} else if (result == 'Hapus') {
				dlg = $dialogs.notify('Informasi', 'Data Sukses Dihapus');
			}
			break;
		case '500':
			if (result == 'Simpan') {
				dlg = $dialogs.error('Data Gagal Disimpan');
			} else if (result == 'Hapus') {
				dlg = $dialogs.error('Data Gagal Dihapus');
			}
			break;
		}
	}
	
	
})();
