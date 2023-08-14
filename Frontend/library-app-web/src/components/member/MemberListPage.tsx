import { Card } from "@material-tailwind/react";
import BackgroundImage from "../home/BackgroundImage";
import MemberTable from "./MemberTable";

export default function MemberListPage() {
  return (
    <BackgroundImage>
      <div className="flex items-center justify-center">
        <div className="w-3/4">
          <Card>
            <MemberTable />
          </Card>
        </div>
      </div>
    </BackgroundImage>
  );
}
