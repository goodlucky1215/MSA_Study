const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
    app.use( 
        createProxyMiddleware(['/user-service', '/order-service'],
        {
            target: 'http://localhost:7500',// 서버 URL or localhost:설정한포트번호
            changeOrigin: true,
        })
    );

};

module.exports = function(app) {
    app.use( 
        createProxyMiddleware(['/seller-service','/login'],
        {
            target: 'http://localhost:9602',// 서버 URL or localhost:설정한포트번호
            changeOrigin: true,
        })
    );

};