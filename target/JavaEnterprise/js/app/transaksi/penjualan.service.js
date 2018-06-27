(function() {

	var services = angular.module('je-transaksi-penjualan-service', [ 'ngResource' ]);

	
	services.factory('PenjualanListFactory', [ '$resource', function($resource) {

		return $resource('service/transaksi/penjualan', {}, {
			query : {
				method : 'GET',
				isArray : false
			}
		});

	} ]);
    
        services.factory('PenjualanCreateFactory', [ '$resource', function($resource) {

                    return $resource('service/transaksi/penjualan', {}, {
                            create : {
                                    method : 'POST'
                            }
                    });

        } ]);
	
	services.factory('PenjualanEditFactory', [ '$resource', function($resource) {

		return $resource('service/transaksi/penjualan/:kode', {}, {
			show : {
				method : 'GET',
				params: {kode: '@kode'}
			},
			update : {
				method : 'PUT',
				params :{kode: '@kode'}
			}
		});

	} ]);
    
    
        services.factory('PenjualanDeleteFactory', [ '$resource', function($resource) {

		return $resource('service/transaksi/penjualan/:kode', {}, {
			delete : {
				method : 'DELETE',
				params :{kode: '@kode'}
			}
		});

	} ]);
	
})();