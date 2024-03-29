const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
    app.use( 
        createProxyMiddleware(['/user-service', '/order-service','/seller-service'],
        {
            target: 'http://localhost:7500',// 서버 URL or localhost:설정한포트번호
            changeOrigin: true,
        })
    );

};