const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
    app.use( 
        createProxyMiddleware(['/user-service', '/login'],
        {
            target: 'http://localhost:9600',// 서버 URL or localhost:설정한포트번호
            changeOrigin: true,
        })
    );
};