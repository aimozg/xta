var path = require("path");
// expose assets to webpack
config.resolve.modules.push(
	path.join(config.output.path,"../..","assets")
);
