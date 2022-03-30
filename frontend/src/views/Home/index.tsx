import MenuRouterActivity from "components/MenuRouterActivity";

const HomeView = () => {
  return (
    <main className="content-main animate-right">
    <div className="home-header">
        <h1>{"GCSystem"}</h1>
    </div>
    <MenuRouterActivity />
    <div>
      <p>home</p>
    </div>
    </main>
  )
}

export default HomeView;
