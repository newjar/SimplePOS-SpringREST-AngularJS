(function() {

	var app = angular.module('je-masterdata-customer-controller', [
			'je-masterdata-customer-service',
			'ui.bootstrap', 'dialogs' ]);

	app.controller('CustomerListController', [ '$scope', '$location',
			'$dialogs', 'CustomerListFactory','CustomerDeleteFactory',
			function($scope, $location, $dialogs, CustomerListFactory,CustomerDeleteFactory) {

				$scope.currentPage = 1;
				$scope.totalCustomer = 0;
				$scope.pageSize = 10;

				$scope.pagination = {
					current : 1
				};

				//ordering
				$scope.predicate='nama';
                                $scope.reverse=false;
	            
				$scope.pageChanged = function(newPage) {
					$scope.gridPromise = CustomerListFactory.query({
						activePage : newPage,
						order : $scope.predicate + "-" + ($scope.reverse ? "desc" : "asc")
					}, function(data) {
						$scope.customerList = data.list;
						notif($dialogs, data.status, 'List');
						$scope.totalCustomer = data.total;
					});
				};
				$scope.pageChanged(1);

				$scope.search = function() {
					$scope.currentPage = 1;
					$scope.pageChanged(1);
				};

				$scope.create = function() {
					$location.path('/masterdata/customer/create');
				};
				
			   $scope.delete = function (customer) {
		        	dlg = $dialogs.confirm('Konfirmasi','Apakah anda ingin hapus Customer : ' + customer.nama);
		        	dlg.result.then(function(btn){
		        		CustomerDeleteFactory.delete({kode: customer.kode}, function(data){
		        			notif($dialogs, data.status, 'Hapus');
		            		$scope.search();
		    			});
		            	
		            },function(btn){
		              //canceled
		            });
		            
		        };
		        
                        $scope.edit = function (customer) {
                            $location.path('/masterdata/customer/' + customer.kode + '/edit');
		        };
		        
		        $scope.detail = function (customer) {
		            $location.path('/masterdata/customer/' + customer.kode + '/detail');
		        };

			} ]);
                    
        app.controller('CustomerDetailController', [
                        '$scope',
                        '$routeParams',
                        '$location',
                        'CustomerEditFactory',
                        function($scope, $routeParams, $location, CustomerEditFactory) {

		$scope.title = "Info Detail Customer";

		CustomerEditFactory.show({
			kode : $routeParams.kode
		}, function(data) {
			$scope.customer = data.customer;
		});


		$scope.cancel = function() {
			$location.path('/masterdata/customer');
		};

	} ]);

        app.controller('CustomerCreateController', [
                '$scope',
                '$routeParams',
                '$location',
                '$dialogs',
                'CustomerCreateFactory',
                function($scope, $routeParams, $location,$dialogs,
                                CustomerCreateFactory) {

                        $scope.title = "Buat Baru Customer";
                        $scope.isEdit = false;

                        $scope.customer = {

                        };
                        $scope.save = function() {
                                CustomerCreateFactory.create({
                                        customer : $scope.customer
                                }, function(data) {
                                        notif($dialogs, data.status, 'Simpan');
                                        $location.path('/masterdata/customer');
                                });

                        };

                        $scope.cancel = function() {
                                $location.path('/masterdata/customer');
                        };

                } ]);

	app.controller('CustomerEditController', [
                '$scope',
                '$routeParams',
                '$location',
                '$dialogs',
                'CustomerEditFactory',
                function($scope, $routeParams, $location, $dialogs,CustomerEditFactory) {

                        $scope.title = "Ubah Customer";
                        $scope.isEdit = true;

                        CustomerEditFactory.show({
                                kode : $routeParams.kode
                        }, function(data) {
                                $scope.customer = data.customer;
                        });

                        $scope.save = function() {
                                CustomerEditFactory.update({
                                        kode : $scope.customer.kode,
                                        customer : $scope.customer
                                }, function(data) {
                                        notif($dialogs, data.status, 'Simpan');
                                        $location.path('/masterdata/customer');
                                });

                        };

                        $scope.cancel = function() {
                                $location.path('/masterdata/customer');
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
