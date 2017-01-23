module.exports = {
    devtool: 'source-map',
    module: {
        loaders: [
            {
                test:    /\.js/,
                exclude: /node_modules/,
                loaders: ['babel-loader']
            }
        ]
    },
    resolve: {
        extensions: ['.js']
    }
};
